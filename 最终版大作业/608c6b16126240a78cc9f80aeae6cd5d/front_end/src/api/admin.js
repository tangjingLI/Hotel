import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getAllManagers`,
        method: 'POST'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
export function  deleteByManagerIdAPI(managerId) {
    return axios({
        url:`${api.adminPre}/deleteByManagerId/${managerId}`,
        method:'GET'
    })

}