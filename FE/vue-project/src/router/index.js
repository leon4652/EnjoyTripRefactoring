import Vue from "vue";
import VueRouter from "vue-router";


Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html

const routes = [
  {
    path: "/",
    name: "Home",
    component: () => import(/* webpackChunkName: "Home" */ "@/views/HomeView.vue"),
  },
  {
    path: "/test",
    name: "TEST",
    component: () => import(/* webpackChunkName: "Home" */ "@/views/TEST.vue"),
  },
  {
    path: "/login",
    name: "UserLogin",
    component: () => import(/* webpackChunkName: "Home" */ "@/views/user/UserLogin.vue"),
  },
  {
    path: "/register",
    name: "UserRegister",
    component: () => import(/* webpackChunkName: "Home" */ "@/views/user/UserRegister.vue"),
  },
  {
    path: "/mypage",
    name: "MyPage",
    component: () => import(/* webpackChunkName: "Home" */ "@/views/user/mypage/MyPage.vue"),
  },
  {
    path: "/first-auth",
    name: "FirstAuth",
    component: () => import(/* webpackChunkName: "Home" */ "@/views/user/UserFirstAuth.vue"),
  },
  {
    path: "/freeboard",
    name: "FreeBoard",
    component: () => import(/* webpackChunkName: "Home" */ "@/views/freeboard/FreeBoard.vue"),
  },



];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
