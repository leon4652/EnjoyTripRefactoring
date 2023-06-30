## 확인 내용

### 메인

로그인 후 '채팅' NavBar를 통해서 채팅 창에 접근할 수 있습니다.

![](https://velog.velcdn.com/images/csg1353/post/7301141f-a6af-4e84-af8f-8e06a58a2063/image.JPG)

### 실시간 채팅

채팅에서는 작성 시간과 회원 이름, 채팅 내용이 표기됩니다.

![](https://velog.velcdn.com/images/csg1353/post/7e2c47ba-ba5e-464d-897c-e0d8274118fa/image.JPG)

![](https://velog.velcdn.com/images/csg1353/post/71de3a0c-5216-43c7-ba9b-5350cfaa0c83/image.JPG)

![](https://velog.velcdn.com/images/csg1353/post/bd9cac5a-8495-44d4-ac65-daefee9d97ff/image.JPG)


### DB(JPA 사용)

![](https://velog.velcdn.com/images/csg1353/post/11ac560c-be6d-42a5-bdbe-7bc6bac4eeeb/image.png)

MySQL에 대화 로그가 저장됩니다.

## 코드(Vue.js)

### ChatPage.vue

```javascript
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
```

## 코드(SpringBoot)

### WebSocketConfig

```java

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Override
    // 메시지브로커 설정 - endpoint
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/send");
    }

    @Override
    // 웹소켓 연결 위한 endpoint 설정
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/")
                // .setAllowedOrigins("*")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }
}
```

### ChatController
```java

@Controller
public class ChatController {

    private final ChatRepository chatRepository;

    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @MessageMapping("/receive") // /receive를 메시지를 받을 endpoint로 설정합니다. (메시지 매핑)
    @SendTo("/send") // /send로 메시지를 반환합니다. (config)
    public ChatMessage SocketHandler(ChatMessage chatMessage) {
        String userName = chatMessage.getUserName();
        String content = chatMessage.getContent();
        LocalDateTime chatTime = chatMessage.getChatTime();


        //MySQL DB 저장 (JPA 레포지토리 사용)
        chatRepository.save(new ChatEntity(chatTime, userName, content));
        System.out.println("저장 완료");
        // 메시지 타입 FE로 반환
        return new ChatMessage(userName, content, chatTime);
    }

}

```

### WebSocketEventsController.java

```java

@Controller
public class WebSocketEventsController {
    private Set<String> connectedUsers = ConcurrentHashMap.newKeySet();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @EventListener
    public void handleSessionConnectedEvent(SessionConnectedEvent event) {
        String sessionId = event.getMessage().getHeaders().get("simpSessionId").toString();
        connectedUsers.add(sessionId);
        System.out.println("User Connected. Total Users: " + connectedUsers.size());

        simpMessagingTemplate.convertAndSend("/connected", connectedUsers.size()); 
    }

    @EventListener
    public void handleSessionDisconnectEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        connectedUsers.remove(sessionId);
        System.out.println("User Disconnected. Total Users: " + connectedUsers.size());

        simpMessagingTemplate.convertAndSend("/connected", connectedUsers.size());
    }
}


```
