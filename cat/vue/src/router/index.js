import Vue from 'vue'
import VueRouter from 'vue-router'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect:'/loginusers',
  },
  {
    path: '/loginusers',
    name: 'loginusers',
    component:()=>import("@/views/loginusers"),
    meta: {
      title:'用户登录'
    }
  },
  {
    path: '/loginadmin',
    name: 'loginadmin',
    component:()=>import("@/views/loginadmin"),
    meta: {
      title:'管理员登录'
    }
  },
  {
    path: '/register',
    name: 'register',
    component:()=>import("@/views/register"),
    meta: {
      title:'用户注册'
    }
  },
  {
    path: '/cat&people',
    name: 'cat&people',
    component:()=>import("@/views/cat&people"),
    redirect:'/index',
    children:[
      {
        path: '/index',
        name: 'index',
        component:()=>import("@/views/index"),
        meta: {
          title:'养猫人-首页'
        }
      },
      {
        path: '/adopt',
        name: 'adopt',
        component:()=>import("@/views/adopt"),
        meta: {
          title:'养猫人-领养小猫'
        }
      },
      {
        path: '/goods',
        name: 'goods',
        component:()=>import("@/views/goods"),
        meta: {
          title:'养猫人-小猫用品'
        }
      },
      {
        path: '/post',
        name: 'post',
        component:()=>import("@/views/post"),
        meta: {
          title:'养猫人-喵圈'
        }
      },
      {
        path: '/personal',
        name: 'personal',
        component:()=>import("@/views/personal"),
        redirect:'/personal_info',
        children:[
          {
            path: '/personal_info',
            name: 'personal_info',
            component:()=>import("@/views/personal_info"),
            meta: {
              title:'个人中心-我的资料'
            }
          },
          {
            path: '/personal_post',
            name: 'personal_post',
            component:()=>import("@/views/personal_post"),
            meta: {
              title:'个人中心-我的喵圈'
            }
          },
          {
            path: '/personal_adopt',
            name: 'personal_adopt',
            component:()=>import("@/views/personal_adopt"),
            meta: {
              title:'个人中心-领养记录'
            }
          },
          {
            path: '/personal_cart',
            name: 'personal_cart',
            component:()=>import("@/views/personal_cart"),
            meta: {
              title:'个人中心-购物车'
            }
          },
          {
            path: '/personal_order',
            name: 'personal_order',
            component:()=>import("@/views/personal_order"),
            meta: {
              title:'个人中心-历史订单'
            }
          },
        ]
      },
    ]
  },
  {
    path: '/admin',
    name: 'admin',
    component:()=>import("@/views/admin"),
    redirect:'/manage_adopt',
    children:[
      {
        path: '/manage_adopt',
        name: 'manage_adopt',
        component:()=>import("@/views/manage_adopt"),
        meta: {
          title:'领养小猫管理'
        }
      },
      {
        path: '/manage_apply',
        name: 'manage_apply',
        component:()=>import("@/views/manage_apply"),
        meta: {
          title:'领养申请审批'
        }
      },
      {
        path: '/manage_goods',
        name: 'manage_goods',
        component:()=>import("@/views/manage_goods"),
        meta: {
          title:'小猫用品管理'
        }
      },
      {
        path: '/manage_post',
        name: 'manage_post',
        component:()=>import("@/views/manage_post"),
        meta: {
          title:'喵圈管理'
        }
      },
      {
        path: '/manage_users',
        name: 'manage_users',
        component:()=>import("@/views/manage_users"),
        meta: {
          title:'用户资料管理'
        }
      },
      {
        path: '/manage_adopt_history',
        name: 'manage_adopt_history',
        component:()=>import("@/views/manage_adopt_history"),
        meta: {
          title:'领养记录管理'
        }
      },
      {
        path: '/manage_order',
        name: 'manage_order',
        component:()=>import("@/views/manage_order"),
        meta: {
          title:'历史订单管理'
        }
      },
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next) =>{
  if(to.meta.title){
    document.title = to.meta.title
  }
  next();
})

export default router
