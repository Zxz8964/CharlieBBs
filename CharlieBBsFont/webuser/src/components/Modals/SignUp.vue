<template>
  <div class="text-center">
    <v-dialog v-model="dialog" width="500">
      <v-card>
        <v-card-title class="modal-title headline grey lighten-2" primary-title>
          Sign Up
        </v-card-title>
        <v-card-text>
          By creating a Reddit account, you can join, vote, and comment on all your favourite Reddit content.
        </v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation @submit.prevent="submit">
          <v-text-field
            class="signup-email"
            label="Email"
            v-model="email"
            :rules="emailRules"
            color="grey"
            @keydown="error = null"
          ></v-text-field>
          <v-text-field
            class="signup-username"
            label="Username"
            v-model="username"
            :rules="usernameRules"
            color="grey"
            @keydown="error = null"
          ></v-text-field>
          <v-text-field
            class="signup-password"
            label="Password"
            v-model="password"
            :rules="passwordRules"
            color="grey"
            @keydown="error = null"
            type="password"
          ></v-text-field>
          <input type="submit" style="display: none;" />
        </v-form>

        <v-card-text class="error-text" v-if="error">
          {{ error }}
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" @click="submit" min-width="90" :loading="loading">
            <template v-slot:loader>
              <span class="custom-loader">
                <v-icon dark>mdi-cached</v-icon>
              </span>
            </template>
            Sign Up
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      valid: true,
      email: '',
      username: '',
      password: '',
      emailRules: [
        (v) => !!v || 'E-mail is required',
        (v) => (v && v.length <= 254) || 'Email must be less than 255 characters',
        (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid'
      ],
      usernameRules: [
        (v) => !!v || 'Username is required',
        (v) => (v && v.length >= 3 && v.length <= 15) || 'Username must be between 3 and 15 characters'
      ],
      passwordRules: [
        (v) => !!v || 'Password is required',
        (v) => (v && v.length >= 6 && v.length <= 30) || 'Password must be between 6 and 30 characters'
      ],
      error: null,
      loading: false
    };
  },
  methods: {
    async submit() {
      if (!this.$refs.form.validate()) {
        return;
      }

      this.loading = true;

      try {
        const response = await axios.post(
          '/api/users/register',
          {
            email: this.email,
            username: this.username,
            password: this.password
          },
          {
            headers: {
              'Content-Type': 'application/json'
            }
          }
        );

        if (response.status === 200) {
          console.log('Registration success with token:', response.data);
          localStorage.setItem('token', response.data); // 存储JWT到localStorage
          this.$store.commit('setAuthenticated', true);
          this.$store.commit('setUsername', this.username);
          window.location.href = '/';
        } else {
          this.error = response.data.message || '注册失败';
        }
      } catch (error) {
        console.error('Error during registration:', error);
        this.error =
          '注册失败: ' +
          (error.response && error.response.data.message
            ? error.response.data.message
            : error.message);
      } finally {
        this.loading = false;
      }
    }
  },
  computed: {
    dialog: {
      get() {
        return this.$store.state.modal === 'sign-up';
      },
      set() {
        this.$store.commit('setModal', null);
      }
    }
  }
};
</script>

<style scoped lang="scss">
  .v-card__text {
    padding: 20px 24px 10px !important;
  }
  form {
    padding: 0 24px 20px;
  }
  .error-text {
    padding-top: 0 !important;
    color: red !important;
    position: relative;
    top: -10px;
  }
</style>
