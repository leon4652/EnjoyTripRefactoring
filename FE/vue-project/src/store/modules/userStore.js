//import { apiInstance } from "@/api/http.js";

const userStore = {
  namespaced: true,
  state: {
    loggedIn: false,
  },
  mutations: {
    setLoggedIn(state, loggedIn) {
      state.loggedIn = loggedIn;
    },
  },
  actions: {
    login({commit},{id, password}) {
      console.dir(id + " " + password);
      commit('setLoggedIn', true);
      // try {
      //   const response = await apiInstance().post('/login', { id, password });
      //   alert(response);
      //   commit('setLoggedIn', true);
  
      // } catch (error) {

      //   console.error(error);
      // }
    },
    logout({ commit }) {
      // 로그아웃 처리 로직
      commit('setLoggedIn', false);
    },
  },
};

export default userStore;
