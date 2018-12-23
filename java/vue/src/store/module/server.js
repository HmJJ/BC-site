import { connect } from '@/api/server'

export default {
  actions: {
    connect ({ip, port, userName, userPwd}) {
      console.log(ip, port, userName, userPwd)
      return new Promise((resolve, reject) => {
        connect({ip, port, userName, userPwd}).then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    }
  }
}