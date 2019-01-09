import axios from '@/libs/api.request'

export const checkVersionFabric = () => {
  return axios.request({
    url: 'buildFabricEnv/checkVersion',
    params: {},
    method: 'post'
  })
}

export const oneTapFabric = () => {
  return axios.request({
    url: 'buildFabricEnv/oneTap',
    params: {},
    method: 'post'
  })
}

export const subTapFabric = ({name}) => {
  return axios.request({
    url: 'buildFabricEnv/subTap',
    params: {
      name
    },
    method: 'post'
  })
}
