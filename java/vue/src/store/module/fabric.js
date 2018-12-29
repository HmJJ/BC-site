import { oneTap, subTap, checkVersion } from '@/api/fabric'

export default {
  actions: {
    checkVersion ({commit}) {
      return new Promise((resolve, reject) => {
        checkVersion().then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    oneTap ({commit}) {
      return new Promise((resolve, reject) => {
        oneTap().then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    subTap ({commit}, {cmd}) {
      return new Promise((resolve, reject) => {
        subTap({cmd}).then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    }
  }
}
