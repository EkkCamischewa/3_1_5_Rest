import * as utils from "./util.js"

$(document).ready(async function () {

    await updateUsersTable()

    $("#submitEditBtn").bind("click", async function () {
        // Получаем данные для обновления пользователя из полей формы
        let patchedUser = {
            id: $("#editId").val(),
            name: $("#editName").val(),
            age: $("#editAge").val(),
            username: $("#editUsername").val(),
            password: $("#editPassword").val(),
            roles: []
        }
        // Получаем выбранные роли пользователя из выпадающего списка
        for (let option of $("#editRoles").children()) {
            if (option.selected) {
                let patchedRoleId = option.value
                await fetch("/roles/" + patchedRoleId)
                    .then(response => response.json())
                    .then(role => {
                        patchedUser.roles.push(role)
                    })
            }
        }
        // Выполняем PATCH-запрос на сервер для обновления пользователя
        let patchedUsername = $("#editId").val()
        await fetch("/users/" + patchedUsername + "/edit", {
            method: "PATCH",
            body: JSON.stringify(patchedUser),
            headers: {
                "Content-Type": "application/json"
            }
        })

        if ($("#editId").val() === await utils.getAuthId()) {
            await utils.updateUserInfo()
        }

        // Если редактируемый пользователь - текущий пользователь, обновляем информацию о нем
        await updateUsersTable()

        // Обновляем таблицу пользователей после успешного обновления
        $("#editModal").modal("hide")
    })


    $("#submitDeleteBtn").bind("click", async function () {
        // Получаем ID пользователя для удаления
        let deletedId = $("#deleteId").val()
        // console.log("Deleted ID:", deletedId);
        // Если удаляемый пользователь - текущий пользователь, выполняем выход из системы
        if (deletedId === await utils.getAuthId()) {
            window.location = "/logout"
        }
        // Выполняем DELETE-запрос на сервер для удаления пользователя
        await fetch("/users/" + deletedId + "/delete", {
            method: "DELETE"
        })

        await updateUsersTable()
        $("#deleteModal").modal("hide")
    })


    $("#submitNewBtn").bind("click", async function () {
        // Получаем данные нового пользователя из полей формы
        let createdUser = {
            name: $("#newName").val(),
            age: $("#newAge").val(),
            username: $("#newUsername").val(),
            password: $("#newPassword").val(),
            roles: []
        }
        $("#newName").val("")
        $("#newUsername").val("")
        $("#newPassword").val("")
        $("#newAge").val("")
        // Получаем выбранные роли нового пользователя из выпадающего списка
        for (let option of $("#newRoles").children()) {
            if (option.selected) {
                await fetch("/roles/" + option.value)
                    .then(response => response.json())
                    .then(role => {
                        createdUser.roles.push(role)
                    })
                option.selected = false
            }
        }
        await fetch("/users/new", {
            method: "POST",
            body: JSON.stringify(createdUser),
            headers: {
                "Content-Type": "application/json"
            }
        })
        await updateUsersTable()
        window.location = "/admin"
    })
})

async function updateUsersTable() {
    let body = $(".table #allUsers")
    body.empty()
    let users = await fetch("/users/all")
        .then(response => response.json())
        .then(users => {
            return users
        })
    for (let user of users) {
        let tr = $("<tr/>")
        let th = $("<th/>")

        th.text(user.id)
        tr.append(th)

        let tdName = $("<td/>")
        tdName.text(user.name)
        tr.append(tdName)

        let tdAge = $("<td/>")
        tdAge.text(user.age)
        tr.append(tdAge)

        let tdUsername = $("<td/>")
        tdUsername.text(user.username)
        tr.append(tdUsername)

        body.append(tr)

        let tdRoles = $("<td/>")

        let roles = ""
        for (let role of user.roles) {

            roles += `${role.name.substring(5)} `
        }
        tdRoles.text(roles)
        tr.append(tdRoles)

        let tdEdit = $("<td/>")
        let editBtn = $("<button id='editBtn' class='btn btn-primary' type='button'>")
        editBtn.text("Edit")
        editBtn.val(user.id)
        editBtn.bind("click", editFunc)
        tdEdit.append(editBtn)
        tr.append(tdEdit)

        let tdDelete = $("<td/>")
        let deleteBtn = $("<button id='deleteBtn' class='btn btn-danger' type='button'>")
        deleteBtn.text("Delete")
        deleteBtn.val(user.id)
        deleteBtn.bind("click", deleteFunc)
        tdDelete.append(deleteBtn)
        tr.append(tdDelete)
    }
}

async function editFunc() {
    let patchedUser = await fetch("/users/" + this.value)
        .then(response => response.json())
        .then(user => {
            return user
        })
    let select = $("#editRoles")
    select.empty()
    $("#editId").val(patchedUser.id)
    $("#editName").val(patchedUser.name)
    $("#editUsername").val(patchedUser.username)
    $("#editPassword").val(patchedUser.password)
    $("#editAge").val(patchedUser.age)
    let roles = await fetch("/roles/all")
        .then(response => response.json())
        .then(rolesList => {
            return rolesList
        })
    for (let role of roles) {
        let option = $("<option/>")
        option.val(role.id)
        option.text(role.name)
        for (let userRole of patchedUser.roles) {
            if (role.id === userRole.id) {
                option.attr("selected", true)
                break
            }
        }
        select.append(option)
    }
    $("#editModal").modal("show")
}

async function deleteFunc() {
    let deletedUser = await fetch("/users/" + this.value)
        .then(response => response.json())
        .then(user => {
            return user
        })
    let select = $("#deleteRoles")
    select.empty()
    $("#deleteId").val(deletedUser.id)
    $("#deleteName").val(deletedUser.name)
    $("#deleteUsername").val(deletedUser.username)
    $("#deletePassword").val(deletedUser.password)
    $("#deleteAge").val(deletedUser.age)
    for (let role of deletedUser.roles) {
        let option = $("<option/>")
        option.text(role.name)
        select.append(option)
    }
    $("#deleteModal").modal("show")
}
