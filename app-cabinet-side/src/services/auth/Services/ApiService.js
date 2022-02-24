import RequestApi from "../../api";

class ApiService {
  static checkAuth = (token = null) => {
    if (token) {
      return RequestApi.get("/auth/default/get-me", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          include: "",
        },
      });
    }

    return RequestApi.get("/auth/default/get-me", {
      params: {
        include: "",
      },
    });
  };
  static logout = (token = null) => {
    if (token) {
      return RequestApi.get("/auth/default/logout", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
    }

    return RequestApi.get("/auth/default/logout");
  };
}

export default ApiService;
