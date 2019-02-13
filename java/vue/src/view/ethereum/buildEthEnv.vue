<template>
  <div id="main">
    <Card style="width: auto;text-align: left;display: block;margin-left: auto;margin-right: auto;margin-bottom: 20px">
      <div slot="title">
        <Icon type="flash"></Icon>
        {{$t('i18n_eth_onetap')}}
      </div>
      <div>
        <Row>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Go:</p>
          </i-col>
          <i-col span="21" style="text-align:center;margin-bottom: 20px;">
            <p>{{ goInfo }}</p>
          </i-col>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Git:</p>
          </i-col>
          <i-col span="21" style="text-align:center;margin-bottom: 20px;">
            <p>{{ gitInfo }}</p>
          </i-col>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Geth:</p>
          </i-col>
          <i-col span="21" style="text-align:center;margin-bottom: 20px;">
            <p>{{ gethInfo }}</p>
          </i-col>
          <i-col span="24" style="margin-bottom: 20px;">
            <hr />
          </i-col>
          <i-col span="20" style="text-align:center;margin-bottom: 10px;">
            <Button shape="circle" style="width:60%" @click="oneTapBuild()">开始部署</Button>
          </i-col>
          <i-col span="4" style="text-align:left;margin-bottom: 10px;">
            <Button shape="circle" style="width:80%" @click="privateChainBuild()">搭建并测试私有链</Button>
          </i-col>
          <i-col span="24" style="text-align:center;margin-bottom: 10px;">
            <p>{{ gethStatus }}</p>
          </i-col>
        </Row>
      </div>
      <Spin size="large" fix v-if="spinShowOneTap"></Spin>
    </Card>
    <Card style="width: auto;text-align: left;display: block;margin-left: auto;margin-right: auto;margin-bottom: 20px">
      <div slot="title">
        <Icon type="ios-pulse"></Icon>
        {{$t('i18n_eth_dividetap')}}
      </div>
      <div>
        <Row>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Go:</p>
          </i-col>
          <i-col span="15" style="text-align:center;margin-bottom: 20px;">
            <p>{{ goInfo }}</p>
          </i-col>
          <i-col span="6" style="text-align:center;margin-bottom: 20px;">
            <Button shape="circle" style="width:60%" @click="subTapBuild('go')">安装Go</Button>
          </i-col>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Git:</p>
          </i-col>
          <i-col span="15" style="text-align:center;margin-bottom: 20px;">
            <p>{{ gitInfo }}</p>
          </i-col>
          <i-col span="6" style="text-align:center;margin-bottom: 20px;">
            <Button shape="circle" style="width:60%" @click="subTapBuild('git')">安装Git</Button>
          </i-col>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Geth:</p>
          </i-col>
          <i-col span="15" style="text-align:center;margin-bottom: 20px;">
            <p>{{ gethInfo }}</p>
          </i-col>
          <i-col span="6" style="text-align:center;margin-bottom: 20px;">
            <Button shape="circle" style="width:60%" @click="subTapBuild('eth')">安装Geth</Button>
          </i-col>
        </Row>
      </div>
      <Spin size="large" fix v-if="spinShowSubTap"></Spin>
    </Card>
  </div>
</template>
<script>
import { mapActions } from 'vuex'
export default {
  data () {
    return {
      goInfo: '待检测',
      gitInfo: '待检测',
      gethInfo: '待检测',
      gethStatus: '待检测',
      spinShowOneTap: false,
      spinShowSubTap: false
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    ...mapActions(['oneTapEth', 'subTapEth', 'checkVersionEth', 'buildPrivateChain']),
    init () {
      this.spinShowOneTap = true
      this.spinShowSubTap = true
      this.checkVersionEth().then(res => {
        if (res.code === 200) {
          let results = res.data
          if (results !== undefined) {
            this.goInfo = (results['go'] === '' ? '待检测' : results['go'])
            this.gitInfo = (results['git'] === '' ? '待检测' : results['git'])
            this.gethInfo = (results['geth'] === '' ? '待检测' : results['geth'])
          }
        } else {
          this.$Message.error(res.msg)
        }
        this.spinShowOneTap = false
        this.spinShowSubTap = false
      })
    },
    oneTapBuild () {
      this.spinShowOneTap = true
      this.oneTapEth().then(res => {
        if (res.code === 200) {
          let results = res.data
          if (results !== undefined) {
            this.goInfo = (results['go'] === '' ? '待检测' : results['go'])
            this.gitInfo = (results['git'] === '' ? '待检测' : results['git'])
            this.ethSourceInfo = (results['eth'] === '' ? '待检测' : results['eth'])
          }
          this.$Message.success('部署完成')
        } else {
          this.$Message.error(res.msg)
        }
        this.spinShowOneTap = false
      })
    },
    subTapBuild (name) {
      this.spinShowSubTap = true
      this.subTapEth({name: name}).then(res => {
        if (res.code === 200) {
          let results = res.data
          if (results !== undefined) {
            switch (name) {
              case 'go':
                this.goInfo = (results['go'] === '' ? '待检测' : results['go'])
                break
              case 'git':
                this.gitInfo = (results['git'] === '' ? '待检测' : results['git'])
                break
              case 'eth':
                this.ethSourceInfo = (results['eth'] === '' ? '待检测' : results['eth'])
                break
            }
          }
          this.$Message.success(results[name])
        } else {
          this.$Message.error(res.msg)
        }
        this.spinShowSubTap = false
      })
    },
    privateChainBuild (name) {
      this.spinShowSubTap = true
      this.buildPrivateChain().then(res => {
        if (res.code === 200) {
          let results = res.data
          if (results !== undefined) {
            this.goInfo = (results['go'] === '' ? '待检测' : results['go'])
            this.gitInfo = (results['git'] === '' ? '待检测' : results['git'])
            this.ethSourceInfo = (results['eth'] === '' ? '待检测' : results['eth'])
          }
          this.$Message.success('部署完成')
        } else {
          this.$Message.error(res.msg)
        }
        this.spinShowOneTap = false
      })
    }
  }
}
</script>

<style scoped>
</style>
