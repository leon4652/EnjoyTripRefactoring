<template>
  <div id="app" class="chat-container">

    <h1>실시간 채팅</h1>

    <div class="message-box">
      <div 
        v-for="(item, idx) in msgList" 
        :key="idx" 
        class="message-item"
      >
      [{{ formatDate(item.chatTime) }}]<strong>{{ item.userName }}: </strong> {{ item.content }}
      </div>
    </div>
    <div class="input-area">
      <input 
        v-model="message" 
        type="text" 
        @keyup.enter="sendMessage" 
        class="input-field"
      />
      <button @click="sendMessage" class="send-button">전송</button>
      <p></p>
      <router-link to="/">
      <button>돌아가기</button>
    </router-link>
    </div>
  </div>
</template>


<script>
import { mapState } from "vuex";
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";

export default {
  name: "ChatVue",
  data() {
    return {
      message: "",
      msgList: []
    };
  },
  created() {
    this.connect();
  },
  computed: {
    ...mapState("userStore", ["userInfo"])
  },
  watch: {
    msgList() {
      this.$nextTick(() => {
        this.scrollToBottom(); //메시지 추가할때마다 스크롤 갱신 메서드 실행
      });
    }
  },
  methods: {
    sendMessage() {
      if (this.message !== "") {
        this.send();
        this.message = "";
      }
    },
    send() {
      console.log("Send message:" + this.message);
      if (this.stompClient && this.stompClient.connected) {
        const msg = {
          userName: this.userInfo.userName,
          content: this.message,
          chatTime: new Date() 
        };
        this.stompClient.send("/receive", JSON.stringify(msg), {});
      }
    },
    connect() {
      const serverURL = "http://localhost:80";
      let socket = new SockJS(serverURL);
      this.stompClient = Stomp.over(socket);
      console.log(`try to connect socket.. address: ${serverURL}`);
      this.stompClient.connect(
        {},
        frame => {
          this.connected = true;
          console.log("socket connection done", frame);
          // pub sub 구조 : 서버의 메시지 전송의 endpoint를 연결
          this.stompClient.subscribe("/send", res => {
            console.log("msg from endpoint", res.body);

            // 받은 데이터 json으로 파싱 후 리스트에 추가.
            this.msgList.push(JSON.parse(res.body));
          });
        },
        error => {
          // 소켓 연결 실패
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
    scrollToBottom() { //스크롤 내리기
      this.$nextTick(() => {
      const container = this.$el.querySelector('.message-box');
      container.scrollTop = container.scrollHeight;
    });
    },
    formatDate(chatTime) { //시간 조정
    const date = new Date(chatTime);
    // const month = date.getMonth() + 1;
    // const day = date.getDate();
    const hours = (date.getHours() + 9) % 24; //GMT + 9 
    const minutes = date.getMinutes();
    const seconds = date.getSeconds();
    
    return `${hours}:${minutes}:${seconds}`;
  },
  }
};
</script>

<style scoped>

.chat-container {
  display: flex;
  flex-direction: column;
  align-items: center; /* 중앙 정렬 */
}

.message-box {
  border: 1px solid #ccc;
  padding: 10px;
  height: 400px;
  width: 400px;
  overflow-y: auto; 
  display: flex; /* flexbox 사용 */
  flex-direction: column; /* 세로로 아이템 정렬 */
  align-items: flex-start; /* 왼쪽 정렬 */
}

.message-item {
  margin-bottom: 10px;
  display: flex; /* flexbox 사용 */
  align-items: center; /* 중앙 정렬 */
}

.input-area {
}
</style>