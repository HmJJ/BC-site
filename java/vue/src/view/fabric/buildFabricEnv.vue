<template>
  <div id="main">
    <Card style="width: auto;text-align: left;display: block;margin-left: auto;margin-right: auto;margin-bottom: 20px">
      <div slot="title">
        <Icon type="flash"></Icon>
        {{$t('i18n_fabric_onetap')}}
      </div>
      <div>
        <Row>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Docker:</p>
          </i-col>
          <i-col span="21" style="text-align:center;margin-bottom: 20px;">
            <p>{{ dockerInfo }}</p>
          </i-col>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Docker-Compose:</p>
          </i-col>
          <i-col span="21" style="text-align:center;margin-bottom: 20px;">
            <p>{{ dockerComposeInfo }}</p>
          </i-col>
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
            <p>Fabric-Source:</p>
          </i-col>
          <i-col span="21" style="text-align:center;margin-bottom: 20px;">
            <p>{{ fabricSourceInfo }}</p>
          </i-col>
          <i-col span="24" style="margin-bottom: 20px;">
            <hr />
          </i-col>
          <i-col span="24" style="text-align:center;margin-bottom: 10px;">
            <Button shape="circle" style="width:60%" @click="oneTapBuild()">开始部署</Button>
          </i-col>
        </Row>
      </div>
      <Spin size="large" fix v-if="spinShowOneTap"></Spin>
    </Card>
    <Card style="width: auto;text-align: left;display: block;margin-left: auto;margin-right: auto;margin-bottom: 20px">
      <div slot="title">
        <Icon type="ios-pulse"></Icon>
        {{$t('i18n_fabric_dividetap')}}
      </div>
      <div>
        <Row>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Docker:</p>
          </i-col>
          <i-col span="15" style="text-align:center;margin-bottom: 20px;">
            <p>{{ dockerInfo }}</p>
          </i-col>
          <i-col span="6" style="text-align:center;margin-bottom: 20px;">
            <Button shape="circle" style="width:60%" @click="subTapBuild('docker')">安装docker</Button>
          </i-col>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Docker-Compose:</p>
          </i-col>
          <i-col span="15" style="text-align:center;margin-bottom: 20px;">
            <p>{{ dockerComposeInfo }}</p>
          </i-col>
          <i-col span="6" style="text-align:center;margin-bottom: 20px;">
            <Button shape="circle" style="width:60%" @click="subTapBuild('docker-compose')">安装docker-compose</Button>
          </i-col>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Go:</p>
          </i-col>
          <i-col span="15" style="text-align:center;margin-bottom: 20px;">
            <p>{{ goInfo }}</p>
          </i-col>
          <i-col span="6" style="text-align:center;margin-bottom: 20px;">
            <Button shape="circle" style="width:60%" @click="subTapBuild('go')">安装go</Button>
          </i-col>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Git:</p>
          </i-col>
          <i-col span="15" style="text-align:center;margin-bottom: 20px;">
            <p>{{ gitInfo }}</p>
          </i-col>
          <i-col span="6" style="text-align:center;margin-bottom: 20px;">
            <Button shape="circle" style="width:60%" @click="subTapBuild('git')">安装git</Button>
          </i-col>
          <i-col span="3" style="text-align:right;margin-bottom: 20px;">
            <p>Fabric-Source:</p>
          </i-col>
          <i-col span="15" style="text-align:center;margin-bottom: 20px;">
            <p>{{ fabricSourceInfo }}</p>
          </i-col>
          <i-col span="6" style="text-align:center;margin-bottom: 20px;">
            <Button shape="circle" style="width:60%" @click="subTapBuild('fabric')">下载fabric源码</Button>
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
      dockerInfo: '待检测',
      dockerComposeInfo: '待检测',
      goInfo: '待检测',
      gitInfo: '待检测',
      fabricSourceInfo: '待检测',
      spinShowOneTap: false,
      spinShowSubTap: false
    }
  },
  mounted () {
    this.init()
  },
  methods: {
    ...mapActions(['oneTap', 'subTap', 'checkVersion']),
    init () {
      this.spinShowOneTap = true
      this.spinShowSubTap = true
      this.checkVersion().then(res => {
        if (res.code === 200) {
          let results = res.data
          if (results !== undefined) {
            this.dockerInfo = (results['docker'] === '' ? '待检测' : results['docker'])
            this.dockerComposeInfo = (results['docker-compose'] === '' ? '待检测' : results['docker-compose'])
            this.goInfo = (results['go'] === '' ? '待检测' : results['go'])
            this.gitInfo = (results['git'] === '' ? '待检测' : results['git'])
            this.fabricSourceInfo = (results['fabric'] === '' ? '待检测' : results['fabric'])
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
      this.oneTap().then(res => {
        if (res.code === 200) {
          let results = res.data
          if (results !== undefined) {
            this.dockerInfo = (results['docker'] === '' ? '待检测' : results['docker'])
            this.dockerComposeInfo = (results['docker-compose'] === '' ? '待检测' : results['docker-compose'])
            this.goInfo = (results['go'] === '' ? '待检测' : results['go'])
            this.gitInfo = (results['git'] === '' ? '待检测' : results['git'])
            this.fabricSourceInfo = (results['fabric'] === '' ? '待检测' : results['fabric'])
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
      this.subTap({name: name}).then(res => {
        if (res.code === 200) {
          let results = res.data
          if (results !== undefined) {
            this.dockerInfo = (results['docker'] === '' ? '待检测' : results['docker'])
            this.dockerComposeInfo = (results['docker-compose'] === '' ? '待检测' : results['docker-compose'])
            this.goInfo = (results['go'] === '' ? '待检测' : results['go'])
            this.gitInfo = (results['git'] === '' ? '待检测' : results['git'])
            this.fabricSourceInfo = (results['fabric'] === '' ? '待检测' : results['fabric'])
          }
          this.$Message.success('部署完成')
        } else {
          this.$Message.error(res.msg)
        }
        this.spinShowSubTap = false
      })
    }
  }
}
</script>

<style scoped>
</style>
