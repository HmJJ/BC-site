<template>
  <div id="main">
    <Card style="width: auto;text-align: left;display: block;margin-left: auto;margin-right: auto;margin-bottom: 20px">
      <div slot="title">
        <Icon type="monitor"></Icon>
        {{$t('i18n_server_info')}}
      </div>
      <div>
        <Form ref="serverForm" :model="serverForm" :rules="serverRule" :label-width="100">
          <Row>
            <i-col span="12">
              <FormItem :label="$t('i18n_server_ip')" prop="serverIP" style="width:67%">
                <Input type="text" v-model="serverForm.serverIP"/>
              </FormItem>
            </i-col>
            <i-col span="12">
              <FormItem :label="$t('i18n_server_port')" prop="serverPort" style="width:40%">
                <Input type="text" v-model="serverForm.serverPort"/>
              </FormItem>
            </i-col>
            <i-col span="8">
              <FormItem :label="$t('i18n_server_username')" prop="username" style="width:100%">
                <Input type="text"  v-model="serverForm.username"/>
              </FormItem>
            </i-col>
            <i-col span="8">
              <FormItem :label="$t('i18n_server_password')" prop="password" style="width:100%">
                <Input type="password" v-model="serverForm.password" number />
              </FormItem>
            </i-col>
            <i-col span="6">
              <FormItem style="width:100%">
                <Button type="primary" @click="handleSubmit('serverForm')" >连接</Button>
                <Button @click="handleReset('serverForm')" style="margin-left: 8px" >重置</Button>
              </FormItem>
            </i-col>
          </Row>
        </Form>
      </div>
      <Spin size="large" fix v-if="spinShowConn"></Spin>
    </Card>
    <Card style="width: auto;text-align: left;display: block;margin-left: auto;margin-right: auto;margin-bottom: 20px">
      <div slot="title">
        <Icon type="flash"></Icon>
        {{$t('i18n_common_action')}}
      </div>
      <div>
        <Row>
          <i-col span="24" style="margin-bottom: 20px;">
            <p>{{$t('i18n_server_status')}}: {{ connStatus }}</p>
          </i-col>
          <i-col span="24" style="margin-bottom: 20px;">
            <p>{{$t('i18n_server_info_check')}}:</p>
          </i-col>
          <i-col span="8" style="text-align:center;margin-bottom: 20px;">
            <Button type="ghost" shape="circle" @click="checkInfo('uname -a')" style="width: 50%">内核信息</Button>
          </i-col>
          <i-col span="8" style="text-align:center;margin-bottom: 20px;">
            <Button type="ghost" shape="circle" @click="checkInfo('cat /proc/version')" style="width: 50%">版本信息</Button>
          </i-col>
          <i-col span="8" style="text-align:center;margin-bottom: 20px;">
            <Button type="ghost" shape="circle" @click="checkInfo('cat /etc/redhat-release')" style="width: 50%">发行版信息</Button>
          </i-col>
          <i-col span="8" style="text-align:center;">
            <Button type="ghost" shape="circle" @click="checkInfo('cat /proc/cpuinfo')" style="width: 50%">CPU相关信息</Button>
          </i-col>
          <i-col span="8" style="text-align:center;">
            <Button type="ghost" shape="circle" @click="checkInfo('getconf LONG_BIT')" style="width: 50%">系统运行位数</Button>
          </i-col>
          <i-col span="8" style="text-align:center;">
            <Button type="ghost" shape="circle" @click="checkInfo('free -m')" style="width: 50%">内存使用量</Button>
          </i-col>
          <i-col span="24" style="text-align:center;margin-top:20px">
            <Input v-model="cmdDIY" icon="flash" placeholder="自定义执行" @keyup.enter="keyupEnter" @on-click="executeDIY" style="width: 50%;" />
          </i-col>
        </Row>
      </div>
      <Spin size="large" fix v-if="spinShowExecute"></Spin>
      <Modal v-model="infoModal" width="40%">
        <p slot="header" style="text-align:left">
          <span>{{$t('i18n_server_info')}}</span>
        </p>
        <div style="text-align: center">
          <p style="font-size: 15px;">{{ serverInfo }}</p>
        </div>
        <div slot="footer">
          <Button type="success" long size="default" @click="cancelModal">{{$t('i18n_common_confirm')}}</Button>
        </div>
      </Modal>
    </Card>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    const validateServerIP = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入服务器IP'))
      } else {
        callback()
      }
    }
    const validateUsername = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入服务器用户名'))
      } else {
        callback()
      }
    }
    const validatePassword = (rule, value, callback) => {
      if (!value) {
        callback(new Error('请输入服务器密码'))
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
      connStatus: false,
      infoModal: false,
      serverInfo: 'server search info...',
      spinShowConn: false,
      spinShowExecute: false,
      cmdDIY: ''
    }
  },
  mounted () {
    this.init()
  },
  created () {
    this.keyupEnter()
  },
  methods: {
    ...mapActions(['connect', 'execute', 'getserver']),
    init () {
      this.spinShowConn = true
      this.spinShowExecute = true
      this.getserver().then(res => {
        if (res.code === 200) {
          let serverVO = res.data['serverVO']
          if (serverVO !== undefined) {
            this.serverForm.serverIP = (serverVO.ip === null ? '' : serverVO.ip)
            this.serverForm.serverPort = (serverVO.port === null ? 22 : serverVO.port)
            this.serverForm.username = (serverVO.userName === null ? '' : serverVO.userName)
            this.serverForm.password = (serverVO.userPwd === null ? '' : serverVO.userPwd)
          }
          this.connStatus = res.data['connStatus']
        } else {
          this.$Message.error('服务器参数错误，请检查!')
        }
        this.spinShowConn = false
        this.spinShowExecute = false
      })
    },
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.spinShowConn = true
          this.connStatus = false
          var params = {ip: this.serverForm.serverIP, port: this.serverForm.serverPort, userName: this.serverForm.username, userPwd: this.serverForm.password}
          this.connect(params).then(res => {
            let result = res
            if (result.code === 200) {
              this.connStatus = res.data['connStatus']
              this.$Message.success('连接成功')
            }
            this.spinShowConn = false
          })
        } else {
          this.$Message.error('服务器参数错误，请检查!')
        }
      })
    },
    handleReset (name) {
      this.$refs[name].resetFields()
    },
    checkInfo (cmd) {
      this.spinShowExecute = true
      this.execute({cmd: cmd}).then(res => {
        if (res.code === 200) {
          this.serverInfo = res.data['result']
          this.connStatus = res.data['connStatus']
          this.infoModal = true
        } else {
          this.$Message.error('服务器参数错误，请检查!')
        }
        this.spinShowExecute = false
      })
    },
    cancelModal () {
      this.infoModal = false
    },
    keyupEnter () {
      document.onkeydown = e => {
        if (e.keyCode === 13 && this.spinShowExecute === false) {
          this.executeDIY()
        }
      }
    },
    executeDIY () {
      if (this.cmdDIY === '') {
        this.$Message.error('参数为空！')
      } else {
        this.checkInfo(this.cmdDIY)
      }
    }
  }
}
</script>

<style scoped>
</style>
