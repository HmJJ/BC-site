import { connect, execute, getserver } from '@/api/server'

export default {
  actions: {
    connect ({ commit }, {ip, port, userName, userPwd}) {
      return new Promise((resolve, reject) => {
        connect({ip, port, userName, userPwd}).then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    execute ({ commit }, {cmd}) {
      return new Promise((resolve, reject) => {
        execute({cmd}).then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    getserver ({commit}) {
      return new Promise((resolve, reject) => {
        getserver().then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    }
  }
}
