import axios from '@/libs/api.request'

export const checkVersion = () => {
  return axios.request({
    url: 'buildEnv/checkVersion',
    params: {},
    method: 'post'
  })
}

export const oneTap = () => {
  return axios.request({
    url: 'buildEnv/oneTap',
    params: {},
    method: 'post'
  })
}

export const subTap = ({ name }) => {
  return axios.request({
    url: 'buildEnv/subTap',
    params: {
      name
    },
    method: 'post'
  })
}
