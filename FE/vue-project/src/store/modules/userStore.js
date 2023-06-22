import { apiInstance } from "@/api/http.js";
const fetchLoader = apiInstance();

const userStore = {
  namespaced: true,
  state: {
    loggedIn: false,
    userInfo: null,
  },
  mutations: {
    setLoggedIn(state, loggedIn) {
      state.loggedIn = loggedIn;
      if(!loggedIn) {
        state.userInfo = null; //로그아웃시 userInfo 초기화
      }
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo;
    },
  },
  actions: {
    async login({ commit }, { id, password }) {
      const data = [id, password];
      try {
        const response = await fetchLoader.post("user/login", data);
        if (response.data) {
          commit("setLoggedIn", true);
          commit("setUserInfo", response.data);
        } else {
          commit("setLoggedIn", false);
        }
      } catch (error) {
        console.error(error);
      }
    },
    logout({ commit }) {
      // 로그아웃 처리 로직
      commit("setLoggedIn", false);
    },

    async register(_, { id, password, email, name }) {
      const data = [id, password, name, email];
      try {
        const response = await fetchLoader.post("user/register", data);
        if (response.status === 200) {
          console.log("성공");
        } else {
          console.log("실패");
        }
      } catch (error) {
        console.error(error);
      }
    },

    async checkDuplicate(_, {input, type}) {
      try {
        const data = [type, input];
        const response = await fetchLoader.post(`user/checkduplicate`, data);
        if(response.data) {
          return true;
        }
        else {
          return false;
        }
      } catch (error) {
        console.error(error);
      }
      
    }
  },
};

export default userStore;
