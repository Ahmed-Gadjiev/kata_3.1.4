(async () => {
  fetch("http://localhost:8080/api/user")
    .then((resp) => resp.json())
    .then((data) => {
      addDataInUserInformationPage(data);
      renderLogo(data);
    });

  function addDataInUserInformationPage(user) {
    const userInformationPageBody = document.getElementById(
      "user-information-page"
    );

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
    `;

    userInformationPageBody.appendChild(row);
  }

  function renderLogo(user) {
    const logo = document.getElementById("logo");

    logo.innerHTML = `
      <span id="logo-name" class="nav-text text-light">${user.email} admin</span>
      <span class="nav-text text-light">with roles:</span>
      <span>
        ${user.roles.map(
          (role) => `<span class="nav-text text-light">${role.name}</span>`
        )}
      </span>
    `;
  }
})();
