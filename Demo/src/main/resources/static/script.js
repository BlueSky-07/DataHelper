const $ = document.querySelector.bind(document)

const API_SERVER_PATH = `//${location.host}`
const API_SERVER_PORT = 80
const URL = path => `${API_SERVER_PATH}:${API_SERVER_PORT}${path}`

const sendRequest = (url, table, body) => fetch(URL(url), {
    method: 'POST', headers: {'Content-Type': 'application/json'}, body
})
    .then(response => response.json())
    .then(res => {
        console.log(res)
        print(res.result || '后台错误，请打开控制台查看', table)
    })
    .catch(e => {
        console.error(e)
        print('后台错误，请打开控制台查看', table)
    })

const request = (url = '', table = '', data = {}) => sendRequest(url, table, JSON.stringify({table, data}))

const update = (table = '', condition = {}, target = {}) => sendRequest('/update', table, JSON.stringify({
    condition: {table, data: condition}, target: {table, data: target},
}))

const _ = (form = {}, field = '') => form[field] && form[field].value || ""

const _data = table => {
    const form = document.forms[table]
    const data = ({
        students: {
            card: _(form, "card"),
            gender: _(form, "gender"),
            birthday: _(form, "birthday") && new Date(_(form, "birthday")).toISOString() || '',
            height: _(form, "height") && parseFloat(_(form, "height")) || '',
        },
        teachers: {
            name: _(form, "name"),
            age: _(form, "age") && parseFloat(_(form, "age")) || '',
        }
    })[table.replace(/-condition/, '')]
    return [{}].concat(Object.entries(Object.assign({
        id: _(form, "id"),
        name: _(form, "name"),
        ['#size']: _(form, "#size") || '',
        ['#offset']: _(form, "#offset") || '',
    }, data))
        .filter(([, value]) => value)
        .map(([key, value]) => ({[key]: value})))
        .reduce((res, las) => ({...res, ...las}))
}

const print = (message = '', table = '') => { $(`#result-${table}`).innerText = message }
