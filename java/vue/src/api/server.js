import axios from '@/libs/api.request'

export const connect = ({ip, port, userName, userPwd}) => {
  let data = {
    'ip': ip,
    'port': port,
    'userName': userName,
    'userPwd': userPwd
  }
  return axios.request({
    url: 'server/conn',
    data,
    method: 'post'
  })
}

export const execute = ({cmd}) => {
  return axios.request({
    url: 'server/execute',
    params: {
      cmd
    },
    method: 'post'
  })
}
