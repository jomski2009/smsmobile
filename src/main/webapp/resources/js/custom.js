/**
 * Created by jome on 2014/02/28.
 */

$(document).ready(function () {
    //Functionality to pop the modal window for contacts import.
    $('a.contacts-import').click(function () {
        var groupName = $(this).data('groupname');
        var groupId = $(this).data('groupid');
        $('.modal-header h4').html("Import contacts into " + groupName);
        $('.modal-body #addcontacts-form').attr("action", "/contacts/groups/" + groupId + "/addcontacts");

    });

    //Functionality to load the list of contacts on the contactslist modal window.
    $('a.contacts-list').click(function (e) {
        var groupId = $(this).data('groupid');
        var requestUrl = "http://localhost:8080/api/v1/contacts/groups/" + groupId + "/members";

        $.getJSON(requestUrl, function(data){
            var tabledata = '<table class="table"><thead><tr><th></th><th>Cell Number</th><th>First Name</th><th>Last Name</th></tr></thead><tbody>';

            $.each(data.recipients, function(i, item){
                var lineitem = '<tr><td></td><td>'+item.cellnumber+'</td><td>'+item.firstname+'</td><td>'+item.lastname+'</td></tr>';
                console.log(lineitem);
                tabledata = tabledata + lineitem;

            });
            console.log(tabledata);
            tabledata = tabledata + '</tbody></table>';
            $('#contact-list-table').html(tabledata);
        });
    })

});

