(async () => {
  const allRoles = await (
    await fetch("http://localhost:8080/api/allRoles")
  ).json();

  const allUsers = await (
    await fetch("http://localhost:8080/api/allUsers")
  ).json();

  function getModalUpdateForm(user, roles) {
    return `<div class="modal" id="${"exampleModal" + user.id}"
            tabindex="-1"
            aria-labelledby="exampleModalLabel" aria-hidden="true">
          
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Edit
                            user</h5>
                        <button type="button" class="btn-close"
                                data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
          
                    <form align="center" class="updateUser">
                       
                        <div class="modal-body row justify-content-md-center">
                            <div class="col-md-7">
                                <p class="form-label">Id</p>
                                <input type="text" class="form-control" id="updateId${
                                  user.id
                                }"
                                        value="${user.id}" name="id"
                                        readonly/>
                            </div>
                            <p></p>
                            <div class="col-md-7">
                                <p class="form-label">Username</p>
                                <input type="text" class="form-control" id="updateUsername${
                                  user.id
                                }"
                                        value="${user.username}"
                                        name="username"/>
                            </div>
                            <p></p>
          
                            <div class="col-md-7">
                                <p class="form-label">Age</p>
                                <input type="number" class="form-control" id="updateAge${
                                  user.id
                                }"
                                        value="${user.age}"
                                        name="age"/>
                            </div>
                            <p></p>
          
                            <div class="col-md-7">
                                <p class="form-label">Email </p>
                                <input type="email" class="form-control" id="updateEmail${
                                  user.id
                                }"
                                        value="${user.email}"
                                        name="email"/>
                            </div>
                            <p></p>
          
                            <div class="col-md-7">
                                <p class="form-label">Password</p>
                                <input type="password" class="form-control" id="updatePassword${
                                  user.id
                                }"
                                        name="password"/>
                            </div>
                            <p></p>
          
                            <div class="form-group col-md-7">
                                <p class="form-label">Roles</p>
          
                                <select multiple class="form-control" name="role" id="updateRoles${
                                  user.id
                                }">
                                    ${roles.map((role) => {
                                      let roleOption =
                                        document.createElement("option");
                                      if (
                                        user.roles.some(
                                          (obj) => obj.id === role.id
                                        )
                                      ) {
                                        roleOption.setAttribute(
                                          "selected",
                                          "selected"
                                        );
                                      }

                                      roleOption.innerText = role.name;

                                      return roleOption.outerHTML;
                                    })}
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">Close
                            </button>
                            <button type="submit" class="btn btn-success btn">Edit
                            </button>
                        </div>
                    </form>
          
                </div>
            </div>
          </div>`;
  }

  function addUsersInTable(users) {
    const allUsersTableBody = document.getElementById("table-users-body");
    allUsersTableBody.innerHTML = "";
    const modals = allUsersTableBody.getElementsByClassName("modal");

    for (let i = 0; i < modals.length; i++) {
      modals[i].setAttribute("aria-hidden", "true");
    }

    document.body.classList = "";
    document.body.style = "";

    const modal = document.getElementsByClassName("modal-backdrop show")[0];

    if (modal != undefined) {
      modal.outerHTML = "";
    }

    const homeTab = document.getElementById("home-tab");
    homeTab.classList += " active";
    homeTab.setAttribute("aria-selected", "true");

    const profileTab = document.getElementById("profile-tab");
    profileTab.classList = "nav-link";
    profileTab.setAttribute("aria-selected", "false");
    profileTab.setAttribute("tab-index", "-1");

    document.getElementById("home").classList = "tab-pane show active";
    document.getElementById("profile").classList = "tab-pane";

    users.forEach((user, i) => {
      const row = document.createElement("tr");

      row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
    
            <td>
              <span>
                ${user.roles
                  .map((role) => `<span>${role.name}</span>`)
                  .join(" ")}
              </span>
            </td>
    
            <td>
              <button type="button" class="btn btn-info" data-bs-toggle="modal"
                data-bs-target=${"#exampleModal" + user.id}>
                Edit
              </button>
    
              ${getModalUpdateForm(user, allRoles)}  
            </td>
    
            <td>
                <input type="text" value=${user.id} id="userId${
        user.id
      }" hidden/>
                <button class="btn btn-danger deleteUser">
                  Delete
                </button>
            </td>
            `;

      allUsersTableBody.appendChild(row);
    });

    addEventHandlers();
  }

  function addRolesInUserAddForm(roles) {
    document.getElementById("editRoles").innerHTML = `
      ${roles.map((role) => `<option>${role.name}</option>`)}
    `;
  }

  function addEventHandlers() {
    const deleteUser = document.getElementsByClassName("deleteUser");

    for (i = 1; i <= deleteUser.length; i++) {
      const userId = document.getElementById("userId" + i).value;
      deleteUser[i - 1].addEventListener("click", (event) => {
        event.preventDefault();
        fetch(`http://localhost:8080/admin/deleteUser?userId=${userId}`, {
          method: "POST",
        })
          .then((resp) => resp.json())
          .then((data) => addUsersInTable(data))
          .catch((err) => console.log(err));
      });

    }

    // обновление)
    const updateUser = document.getElementsByClassName("updateUser");

    

    for (let i = 0; i < updateUser.length; i++) {
      updateUser[i].addEventListener("submit", (event) => {
        event.preventDefault();

        const newRoles = document.getElementById(
          "updateRoles" + (i + 1)
        ).selectedOptions;

        const user = {
          id: document.getElementById("updateId" + (i + 1)).value,
          username: document.getElementById("updateUsername" + (i + 1)).value,
          age: document.getElementById("updateAge" + (i + 1)).value,
          email: document.getElementById("updateEmail" + (i + 1)).value,
          password: document.getElementById("updatePassword" + (i + 1)).value,
          roles: getRoles(newRoles),
        };

        fetch(`http://localhost:8080/admin/saveUser`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(user),
        })
          .then((resp) => resp.json())
          .then((data) => addUsersInTable(data))
          .catch((err) => console.log(err));
      });
    }
  }

  const getRoles = (selectedRoles) => {
    let rls = [];
    for (j = 0; j < selectedRoles.length; j++) {
      
      rls.push(selectedRoles[j].value);
    }
    return rls;
  };

  const addUser = document.getElementById("addUser");

  addUser.addEventListener("submit", (event) => {
    event.preventDefault();

    const selectedRoles = document.getElementById("editRoles").selectedOptions;

    const newUser = {
      username: document.getElementById("newUsername").value,
      age: document.getElementById("newAge").value,
      email: document.getElementById("newEmail").value,
      password: document.getElementById("newPassword").value,
      roles: getRoles(selectedRoles),
    };

    fetch(`http://localhost:8080/admin/saveUser`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newUser),
    })
      .then((resp) => resp.json())
      .then((data) => {
        console.log(data)
        addUsersInTable(data)})
      .catch((err) => console.log(err));

    addUser.reset();
  });
  
  addUsersInTable(allUsers);
  addRolesInUserAddForm(allRoles);
})();
