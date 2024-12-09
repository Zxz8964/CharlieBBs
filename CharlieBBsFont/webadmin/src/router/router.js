import { createRouter, createWebHistory } from "vue-router";
/* Layout */
import Layout from '@/Layout/Layer.vue'

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/view/redirect/redirect.vue')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/view/login.vue'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/view/register.vue'),
    hidden: true
  },
  {
    path: "/:pathMatch(.*)*",
    component: () => import('@/view/error/404.vue'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/view/error/401.vue'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: '/index',
    children: [
      {
        path: '/index',
        component: () => import('@/view/index.vue'),
        name: 'Index',
        meta: { title: '首页', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/view/system/user/profile/index.vue'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user' }
      }
    ]
  },

  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/view/system/user/authRole.vue'),
        name: 'AuthRole',
        meta: { title: '管理角色', activeMenu: '/system/user' }
      }
    ]
  },

  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/view/system/role/authUser.vue'),
        name: 'AuthUser',
        meta: { title: '管理用户', activeMenu: '/system/role' }
      }
    ]
  },

  {
    path: '/system/permission-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:userId(\\d+)',
        component: () => import('@/view/system/permission/authPermission.vue'),
        name: 'AuthPermission',
        meta: { title: '管理权限', activeMenu: '/system/permission' }
      }
    ]
  },

  {
    path: '/system/banned-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/view/system/banned/bans.vue'),
        name: 'BanUsersList',
        meta: { title: '封禁管理', activeMenu: '/system/banned' }
      }
    ]
  },

  {
    path: '/system/board-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/view/system/board/ForumManagement.vue'),
        name: 'ForumManagement',
        meta: { title: '板块管理', activeMenu: '/system/board' }
      }
    ]
  },

  {
    path: '/system/post-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/view/system/post/postmanage.vue'),
        name: 'postmanage',
        meta: { title: '板块管理', activeMenu: '/system/post' }
      }
    ]
  },
  {
    path: '/system/comment-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/view/system/comment/commentManage.vue'),
        name: 'commentManage',
        meta: { title: '评论管理', activeMenu: '/system/comment' }
      }
    ]
  },
  {
    path: '/system/Category-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/view/system/tag/CategoryTagManager.vue'),
        name: 'CategoryTagManager',
        meta: { title: '分类标签管理', activeMenu: '/system/tag' }
      }
    ]
  },
  {
    path: '/system/report-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/view/system/report/reportManage.vue'),
        name: 'reportManage',
        meta: { title: '举报管理', activeMenu: '/system/report' }
      }
    ]
  },
  {
    path: '/system/LabelManager-auth',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/view/system/tag/LabelManager.vue'),
        name: 'LabelManager',
        meta: { title: '标签管理', activeMenu: '/system/tag' }
      }
    ]
  },
]
// 动态路由，基于用户权限动态去加载
// export const dynamicRoutes = [
//   {
//     path: '/system/user-auth',
//     component: Layout,
//     hidden: true,
//     permissions: ['system:user:edit'],
//     children: [
//       {
//         path: 'role/:userId(\\d+)',
//         component: () => import('@/view/system/user/authRole.vue'),
//         name: 'AuthRole',
//         meta: { title: '分配角色', activeMenu: '/system/user' }
//       }
//     ]
//   },
//   {
//     path: '/system/role-auth',
//     component: Layout,
//     hidden: true,
//     permissions: ['system:role:edit'],
//     children: [
//       {
//         path: 'user/:roleId(\\d+)',
//         component: () => import('@/views/system/role/authUser'),
//         name: 'AuthUser',
//         meta: { title: '分配用户', activeMenu: '/system/role' }
//       }
//     ]
//   },
//   {
//     path: '/system/dict-data',
//     component: Layout,
//     hidden: true,
//     permissions: ['system:dict:list'],
//     children: [
//       {
//         path: 'index/:dictId(\\d+)',
//         component: () => import('@/views/system/dict/data'),
//         name: 'Data',
//         meta: { title: '字典数据', activeMenu: '/system/dict' }
//       }
//     ]
//   },
//   {
//     path: '/monitor/job-log',
//     component: Layout,
//     hidden: true,
//     permissions: ['monitor:job:list'],
//     children: [
//       {
//         path: 'index/:jobId(\\d+)',
//         component: () => import('@/views/monitor/job/log'),
//         name: 'JobLog',
//         meta: { title: '调度日志', activeMenu: '/monitor/job' }
//       }
//     ]
//   },
//   {
//     path: '/tool/gen-edit',
//     component: Layout,
//     hidden: true,
//     permissions: ['tool:gen:edit'],
//     children: [
//       {
//         path: 'index/:tableId(\\d+)',
//         component: () => import('@/views/tool/gen/editTable'),
//         name: 'GenEdit',
//         meta: { title: '修改生成配置', activeMenu: '/tool/gen' }
//       }
//     ]
//   }
// ]

const router = createRouter({
  history: createWebHistory(),
  routes: constantRoutes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  },
});

export default router;