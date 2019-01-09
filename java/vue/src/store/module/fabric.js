import { oneTapFabric, subTapFabric, checkVersionFabric, testFabric } from '@/api/fabric'

export default {
  actions: {
    checkVersionFabric ({commit}) {
      return new Promise((resolve, reject) => {
        checkVersionFabric().then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    oneTapFabric ({commit}) {
      return new Promise((resolve, reject) => {
        oneTapFabric().then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    subTapFabric ({commit}, {name}) {
      return new Promise((resolve, reject) => {
        subTapFabric({name}).then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    },
    testFabric ({commit}) {
      return new Promise((resolve, reject) => {
        testFabric().then(res => {
          resolve(res)
        }).catch(err => {
          reject(err)
        })
      })
    }
  }
}
