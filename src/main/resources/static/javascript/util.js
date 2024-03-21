export async function getAuthUser() {
    let response = await fetch("/users/current")
    return response.ok
        ? response.json()
        : null
}

export async function getAuthId() {
    return await getAuthUser()
        .then(authUser => authUser.id)
        .then(id => {
            return id === undefined ? null : id
        })
}

export async function updateUserInfo() {
    let authUser = await getAuthUser()
    if (authUser != null) {
        $("#userId").text(authUser.id)
        $("#usernameNavbar").text(authUser.name)
        $("#userName").text(authUser.name)
        $("#userAge").text(authUser.age)
        $("#userUsername").text(authUser.username)
        let rolesText = " with roles: "
        for (let role of authUser.roles) {
            rolesText += `${role.name.substring(5)},  `
        }
        $("#userRolesNavbar").text(rolesText.substring(0, rolesText.length - 3))
        $("#userRoles").text(rolesText.substring(12, rolesText.length - 3))
    }
}