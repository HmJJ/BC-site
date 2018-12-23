import axios from '@/libs/api.request'

export const connect = ({ip, port, userName, userPwd}) => {
  return axios.request({
    url: 'server/conn',
    params: {
      ip,
      port,
      userName,
      userPwd
    },
    method: 'post'
  })

}
