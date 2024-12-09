<template>
  <v-app-bar app color="#fff">
    <a href="/"><img id="logo" src="@/assets/images/reddit.png" /></a>

    <v-spacer></v-spacer>
    <GlobalSearch />
    <v-spacer></v-spacer>

    <!-- 菜单栏，仅在移动端显示 -->
    <v-menu offset-y min-width="110" v-if="showHamburgerMenu">
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          data-testid="hamburger-menu"
          dark
          icon
          v-bind="attrs"
          v-on="on"
          color="black"
        >
          <v-icon>menu</v-icon>
        </v-btn>
      </template>

      <v-list>
        <v-list-item
          v-for="(item, i) in menuItems"
          :key="i"
        >
          <v-list-item-title @click="item.action">{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>

    <!-- 登录、注册或用户菜单（头像图标） -->
    <div v-if="!showHamburgerMenu">
      <template v-if="!isAuthenticated">
        <LogInButton />
        <SignUpButton />
      </template>
      <template v-else>
        <!-- 使用 UserMenuModal 和 UserMenuButton 组件 -->
        <UserMenuButton @click="userMenuDialog = true">
        </UserMenuButton>
        <UserMenuModal v-model="userMenuDialog" @logout="logout" @goToSettings="goToSettings" />
      </template>
    </div>
    
    <LogInModal @success="handleAuthSuccess" />
    <SignUpModal @success="handleAuthSuccess" />
  </v-app-bar>
</template>

<script>
import { computed, onMounted } from 'vue';
import { mapState } from 'vuex';
import { useDisplay } from 'vuetify';
import { useStore } from 'vuex';
import LogInModal from '@/components/Modals/LogIn.vue';
import SignUpModal from '@/components/Modals/SignUp.vue';
import UserMenuModal from '@/components/Modals/UserMenuModal.vue';
import LogInButton from '@/components/Buttons/LogIn.vue';
import SignUpButton from '@/components/Buttons/SignUp.vue';
import UserMenuButton from '@/components/Buttons/UserMenu.vue';
import GlobalSearch from '@/components/Core/GlobalSearch.vue';

export default {
  components: {
    LogInModal,
    SignUpModal,
    LogInButton,
    SignUpButton,
    UserMenuModal,
    UserMenuButton,
    GlobalSearch,
  },
  data() {
    return {
      userMenuDialog: false, // 添加状态管理
      itemsAuth: [
        {
          title: 'User Settings',
          action: () => this.goToSettings(),
        },
        {
          title: 'Log Out',
          action: () => this.logout(),
        },
      ],
      itemsNoAuth: [
        {
          title: 'Log In',
          action: () => this.$store.commit('setModal', 'log-in'),
        },
        {
          title: 'Sign Up',
          action: () => this.$store.commit('setModal', 'sign-up'),
        },
      ],
    };
  },
  computed: {
    ...mapState(['isAuthenticated']),
    menuItems() {
      return this.isAuthenticated ? this.itemsAuth : this.itemsNoAuth;
    },
    showHamburgerMenu() {
      return this.display?.xsOnly && this.isAuthenticated;
    },
  },
  methods: {
    logout() {
      localStorage.removeItem('token');
      this.$store.commit('setAuthenticated', false);
      this.$store.commit('setUsername', null);
      this.$router.push('/');
    },
    goToSettings() {
      this.$router.push('/settings');
    },
    handleAuthSuccess(token) {
      localStorage.setItem('token', token);
      this.$store.commit('setAuthenticated', true);
      this.$store.commit('setUsername', 'username from token or API');
      this.$router.push('/');
    }
  },
  setup() {
    const display = useDisplay();
    const store = useStore();

    onMounted(() => {
      const token = localStorage.getItem('token');
      if (token) {
        store.commit('setAuthenticated', true);
        store.commit('setUsername', 'username from token or API');
      }
    });

    return {
      display,
    };
  },
};
</script>

<style scoped lang="scss">
@use 'vuetify/styles';

a {
  display: flex;
  align-items: center;
}

#logo {
  width: 40px;
  cursor: pointer;
}

.cursor-pointer {
  cursor: pointer;
}

.log-in-button {
  margin-right: 20px;
  color: black !important;
}

.v-list {
  padding: 0;
}

.v-list-item {
  cursor: pointer;
  &:hover {
    background: rgb(246, 246, 246);
  }
}

@media (min-width: 600px) {
  .v-list-item__title {
    font-size: 14px !important;
  }
}

@media (max-width: 576px) {
  #logo {
    width: 35px;
  }
}
</style>
