import RequestApi from "./index";

class Api {
    static getAll = (url, config) => {
        return RequestApi.get(url, config);
    }
    static getOne = (url, config) => {
        return RequestApi.get(url, config);
    }
}

export default Api;