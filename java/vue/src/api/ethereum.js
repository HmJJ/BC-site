import axios from '@/libs/api.request'

export const checkVersionEth = () => {
  return axios.request({
    url: 'buildEthEnv/checkVersion',
    params: {},
    method: 'post'
  })
}

export const oneTapEth = () => {
  return axios.request({
    url: 'buildEthEnv/oneTap',
    params: {},
    method: 'post'
  })
}

export const subTapEth = ({name}) => {
  return axios.request({
    url: 'buildEthEnv/subTap',
    params: {
      name
    },
    method: 'post'
  })
}
