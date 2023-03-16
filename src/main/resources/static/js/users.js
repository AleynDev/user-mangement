// Call the dataTables jQuery plugin
$(document).ready(function () {

  loadUsers();

  $('#users').DataTable();

  updateUserEmail();
});

function updateUserEmail() {
  document.getElementById('txtUserEmail').outerHTML = localStorage.email;

}


async function loadUsers() {

  const request = await fetch('api/users', {
    method: 'GET',
    headers: getHeaders(),

  });

  const users = await request.json();

  let htmlUsersList = '';
  for (const user of users) {
    let deleteUserBtn = '<a href="#" onclick="deleteUser(' + user.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    let txtPhone = user.phone !== null ? user.phone : ' - ';
    let htmlUser = '<tr><td>' + user.id + '</td><td>' + user.name + ' ' + user.lastname + '</td>'
      + '<td>' + user.email + '</td><td>' + txtPhone + '</td>'
      + '<td>' + deleteUserBtn + '</td></tr>';

    htmlUsersList += htmlUser;

  }

  console.log(users);

  document.querySelector('#users tbody').outerHTML = htmlUsersList;

}

function getHeaders() {
  return {
    'Accept': 'application/json',
    'Content-Type': 'application/json',
    'Authorization': localStorage.token
  };
}


async function deleteUser(id) {

  if (!confirm('¿Desea eliminar este usuario?')) {
    return;
  }

  const request = await fetch('api/users/' + id, {
    method: 'DELETE',
    headers: getHeaders(),
  });

  location.reload();
}

// body: JSON.stringify({ a: 1, b: 'Textual content' })
