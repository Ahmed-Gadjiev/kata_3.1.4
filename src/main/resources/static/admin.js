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
          
                    <form align="center" method="POST"
                          action="http://localhost:8080/admin/saveUser">
                        <input type="hidden" name="_csrf" value="77e94014-5492-4cf5-a81f-a81b0a598f36">
          
                        <div class="modal-body row justify-content-md-center">
                            <div class="col-md-7">
                                <p class="form-label">Id</p>
                                <input type="text" class="form-control"
                                        value="${user.id}" name="id"
                                        readonly/>
                            </div>
                            <p></p>
                            <div class="col-md-7">
                                <p class="form-label">Username</p>
                                <input type="text" class="form-control"
                                        value="${user.username}"
                                        name="username"/>
                            </div>
                            <p></p>
          
                            <div class="col-md-7">
                                <p class="form-label">Age</p>
                                <input type="number" class="form-control"
                                        value="${user.age}"
                                        name="age"/>
                            </div>
                            <p></p>
          
                            <div class="col-md-7">
                                <p class="form-label">Email </p>
                                <input type="email" class="form-control"
                                        value="${user.email}"
                                        name="email"/>
                            </div>
                            <p></p>
          
                            <div class="col-md-7">
                                <p class="form-label">Password</p>
                                <input type="password" class="form-control"
                                        name="password"/>
                            </div>
                            <p></p>
          
                            <div class="form-group col-md-7">
                                <p class="form-label">Roles</p>
          
                                <select multiple class="form-control" name="role">
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

    users.forEach((user) => {
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
              <form method="POST"
              action="http://localhost:8080/admin/deleteUser(userId=${user.id}">
                <button type="submit" class="btn btn-danger">
                  Delete
                </button>
              </form>
            </td>
            `;

      allUsersTableBody.appendChild(row);
    });
  }

  function addRolesInUserAddForm(roles) {
    document.getElementById("editRoles").innerHTML = `
      ${roles.map((role) => `<option>${role.name}</option>`)}
    `;
  }

  addUsersInTable(allUsers);
  addRolesInUserAddForm(allRoles);
})();
