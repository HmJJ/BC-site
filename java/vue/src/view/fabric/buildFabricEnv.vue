<template>
  <div id="main">
    <Card style="width: auto;text-align: left;display: block;margin-left: auto;margin-right: auto;margin-bottom: 20px">
      <div slot="title">
        <Icon type="flash"></Icon>
        {{$t('i18n_fabric_onetap')}}
      </div>
      <div style="text-align:center;margin-top: 20px;margin-bottom: 20px;">
        <Button shape="circle" style="width:60%">开始部署</Button>
      </div>
      <Spin size="large" fix v-if="spinShowConn"></Spin>
    </Card>
    <Card style="width: auto;text-align: left;display: block;margin-left: auto;margin-right: auto;margin-bottom: 20px">
      <div slot="title">
        <Icon type="ios-pulse"></Icon>
        {{$t('i18n_fabric_dividetap')}}
      </div>
      <div style="position:relative;">
        <div style="text-align:center;height: 100px;margin-top: 20px;">
          <p>{{ dividetapMessage }}</p>
        </div>
        <div style="text-align:center;margin-top: 20px;">
          <Button shape="circle" style="width:60%">开始部署</Button>
        </div>
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
    return {
      dividetapMessage: '请点击下方按钮开始部署'
    }
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
          console.log(serverVO)
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
