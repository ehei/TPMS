const localStorageItem = 'token';

const authProvider = {
    login2: ({ username, password }) => {
        if (username !== 'test' || password !== 'test123') {
            return Promise.reject();
        }
        localStorage.setItem(localStorageItem, username);
        return Promise.resolve();
    },
    login: ({ username, password }) =>  {
        const request = new Request('http://localhost:8080/authentication', {
            method: 'POST',
            body: JSON.stringify({ "username": username, "password": password }),
            headers: new Headers({ 'Content-Type': 'application/json' }),
        });
        return fetch(request)
            .then(response => {
                if (response.status < 200 || response.status >= 300) {
                    throw new Error(response.statusText);
                }
                return response.json();
            })
            .then(auth => {
                localStorage.setItem(localStorageItem, JSON.stringify(auth));
            })
            .catch(() => {
                throw new Error('Network error')
            });
    },
    logout: () => {
        localStorage.removeItem(localStorageItem);
        return Promise.resolve();
    },
    checkAuth: () =>
        localStorage.getItem(localStorageItem) ? Promise.resolve() : Promise.reject(),
    checkError:  (error) => {
        const status = error.status;
        if (status === 401 || status === 403) {
            localStorage.removeItem(localStorageItem);
            return Promise.reject();
        }
        // other error code (404, 500, etc): no need to log out
        return Promise.resolve();
    },
    getIdentity: () => {
        let item = localStorage.getItem(localStorageItem);
        console.log(item);
        if (item == null) return Promise.reject();

        console.log(item);
        let parsed = JSON.parse(item);
        console.log("parsed = " + parsed);
        return Promise.resolve(parsed);
    },
        // Promise.resolve({
        //     id: 'user',
        //     fullName: 'Test User',
        // }),
    getPermissions: () => {
        const item = localStorage.getItem(localStorageItem);
        if (item == null)
            return Promise.resolve('guest');
        let parsed = JSON.parse(item);
        if (parsed.role == null)
            return Promise.resolve('guest');
        return Promise.resolve(parsed.role);
    },
};

export default authProvider;