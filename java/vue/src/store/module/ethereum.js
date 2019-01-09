import { oneTapEth, subTapEth, checkVersionEth } from '@/api/ethereum'

export default {
  actions: {
    checkVersionEth ({commit}) {
      return new Promise((resolve, reject) => {
        checkVersionEth().then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    oneTapEth ({commit}) {
      return new Promise((resolve, reject) => {
        oneTapEth().then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    subTapEth ({commit}, {cmd}) {
      return new Promise((resolve, reject) => {
        subTapEth({cmd}).then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    }
  }
}
