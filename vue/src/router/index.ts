import { createRouter as createVueRouter, createWebHashHistory, Router } from "vue-router";
import LandingPage from "../previous/LandingPage.vue";
import Profile from "../previous/Profile.vue";
import { createAuthGuard } from "@auth0/auth0-vue";
import { App } from 'vue';
import Dashboard from "../views/Dashboard.vue";
import SingleProjectPage from "../previous/SingleProjectPage.vue";
//import TaskPage from "../views/TaskPage.vue";
import ListProjectsReport from "../previous/ListProjectsReport.vue";
import SingleProjectTimeReport from "../previous/SingleProjectTimeReport.vue";
import Display from "../views/Display.vue"



export function createRouter(app: App): Router {
  return createVueRouter({
    routes: [
      {
        path: "/",
        name: "landingPage",
        component: LandingPage
      },
      {
        path: "/profile",
        name: "profile",
        component: Profile,
        beforeEnter: createAuthGuard(app)
      },
      {
        path: "/dash",
        name: "dashboard",
        component: Dashboard,
        beforeEnter: createAuthGuard(app)
      },
      {
        path: "/projects/:id",
        name: "project",
        component: SingleProjectPage,
        beforeEnter: createAuthGuard(app)
      },
      {
        path: "/display",
        name: "display",
        component: Display,
        beforeEnter: createAuthGuard(app)
      },
      {
        path: "/worklog",
        name: "listProjectsReport",
        component: ListProjectsReport,
        beforeEnter: createAuthGuard(app)
      },
      {
        path: "/:userid/worklog/:projectid",
        name: "singleProjectTimeReport",
        component: SingleProjectTimeReport,
        beforeEnter: createAuthGuard(app)
      }
    ],
    history: createWebHashHistory()
  })
}
