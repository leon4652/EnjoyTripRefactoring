<template>
  <div id="app" class="chat-container">

    <h1>실시간 채팅</h1>
    <div class="message-box">
      <div v-for="(item, idx) in msgList" :key="idx" class="message-item" :class="{ 'highlight': item.userName === userInfo.userName && highlightEnabled }">
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
      <button @click="toggleHighlight">강조</button>
      <p></p>
      <router-link to="/">
      <button>돌아가기</button>
    </router-link>
    </div>
  </div>
</template>


<script>
import { mapState } from "vuex";
import Stomp from "webstomp-client"; //Simple Text Oriented Messaging Protocol, 클라이언트와 서버 간의 메시지 교환을 위한 표준화된 프로토콜. 여기서는 메시지브로커와 연결
import SockJS from "sockjs-client";  // WebSocket 프로토콜을 사용하여 웹 애플리케이션에서 실시간 양방향 통신을 구현하는 데 도움을 주는 자바스크립트 라이브러리 (지속 연결)

export default {
  name: "ChatVue",
  data() {
    return {
      message: "",
      msgList: [], //메시지 담긴 배열
      highlightEnabled : false, //내 메시지 강조 표시
      connectedUsers: 0, // 연결된 사용자 수
    };
  },
  created() {
    this.connect();
  },
  mounted() {
    if (this.userInfo === null) {
      alert('로그인 부탁드립니다.');
      this.$router.push("/");
    }
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

        //접속자 수 정보를 받아 처리하는 subscription
        this.stompClient.subscribe("/connected", res => {
        console.log("Connected users update from endpoint", res.body);

        // 받은 데이터를 연결된 사용자 수에 할당. (문자열을 숫자로 변환)
        this.connectedUsers = Number(res.body);
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
    let minutes = date.getMinutes();
    let seconds = date.getSeconds();
    if (minutes < 10) minutes = `0${minutes}`;
    if (seconds < 10) seconds = `0${seconds}`;

    return `${hours}:${minutes}:${seconds}`;
    },
    toggleHighlight() { //내 메시지 강조
      this.highlightEnabled = !this.highlightEnabled;
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
.message-item.highlight {
  background-color: yellow;
}
</style>