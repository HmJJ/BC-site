<template>
  <div id="main">
    <Card style="width: auto;text-align: left;display: block;margin-left: auto;margin-right: auto;margin-bottom: 50px">
      <div slot="title">
        <Icon type="monitor"></Icon>
        {{$t('i18n_server_info')}}
      </div>
      <div>
        <Form ref="serverForm" :model="serverForm" :rules="serverRule" :label-width="100">
          <FormItem :label="$t('i18n_server_ip')" prop="serverIP" style="width:50%">
            <Input type="text" v-model="serverForm.serverIP"/>
          </FormItem>
          <FormItem :label="$t('i18n_server_username')" prop="username" style="width:50%">
            <Input type="text"  v-model="serverForm.username"/>
          </FormItem>
          <FormItem :label="$t('i18n_server_password')" prop="password" style="width:50%">
            <Input type="password" v-model="serverForm.password" number />
          </FormItem>
          <FormItem style="width:50%">
            <Button type="primary" @click="handleSubmit('serverForm')" >Submit</Button>
            <Button @click="handleReset('serverForm')" style="margin-left: 8px" >Reset</Button>
          </FormItem>
        </Form>
        <Spin size="large" fix v-if="spinShow"></Spin>
      </div>
    </Card>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    const validateServerIP = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please enter your Server IP'))
      } else {
        callback()
      }
    }
    const validateUsername = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please enter your server username'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error('password cannot be empty'))
      } else {
        callback()
      }
    }
    return {
      serverForm: {
        serverIP: '',
        serverPort: 22,
        username: '',
        password: ''
      },
      serverRule: {
        serverIP: [
          { validator: validateServerIP, trigger: 'blur' }
        ],
        username: [
          { validator: validateUsername, trigger: 'blur' }
        ],
        password: [
          { validator: validatePassword, trigger: 'blur' }
        ]
      },
      spinShow: false
    }
  },
  methods: {
    ...mapActions(['connect']),
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.spinShow = true
          var params = {ip: serverForm.serverIP, port: serverForm.serverPort, userName: serverForm.username, userPwd: serverForm.password}
          this.connect(params).then(res => {
            let result = res
            if (result.code === 200) {
              this.$Message.success(result.data)
            }
          })
          this.spinShow = false
        } else {
          this.$Message.error('Fail!')
        }
      })
    },
    handleReset (name) {
      this.$refs[name].resetFields()
    }
  }
}
</script>

<style scoped>
</style>
