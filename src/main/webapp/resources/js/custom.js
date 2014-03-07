/**
 * Created by jome on 2014/02/28.
 */

$(document).ready(function () {
    var baseUrl = "http://localhost:8080/api/v1/";

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

        $.getJSON(requestUrl, function (data) {
            var tabledata = '<table class="table"><thead><tr><th></th><th>Cell Number</th><th>First Name</th><th>Last Name</th></tr></thead><tbody>';

            $.each(data.recipients, function (i, item) {
                var lineitem = '<tr><td></td><td>' + item.cellnumber + '</td><td>' + item.firstname + '</td><td>' + item.lastname + '</td></tr>';
                console.log(lineitem);
                tabledata = tabledata + lineitem;

            });
            console.log(tabledata);
            tabledata = tabledata + '</tbody></table>';
            $('#contact-list-table').html(tabledata);
        });
    })

    //Functionality to handle the processing of quick sms
    $('#quick-sms').submit(function (e) {

        var formData = $(this).serialize();
        $.post(baseUrl + "sms/quicksms", formData, function (data) {
            //quicksms-results-table
            var tabledata = '<table class="table"><thead><tr><th></th><th>Recipient</th><th>Total Credits</th><th>Processed</th></tr></thead><tbody>';
            $.each(data.sendMessageResults, function (i, result) {
                console.log(result.destinationAddress + " " + result.price + " " + result.messageStatus);
                var lineitem = '<tr><td></td><td>' + result.destinationAddress + '</td><td>' + result.price + '</td><td>' + result.messageStatus + '</td></tr>';
                tabledata += lineitem;
            });

            tabledata = tabledata + '</tbody></table>';
            $('#quicksms-results-table').html(tabledata);

            console.log(data);
            $('#quicksms-results').modal('show')
            $('#quick-sms').get(0).reset();
        });
        e.preventDefault();
    });

    //Functionality to handle the update of characters left in sms messaging
    $('#messagetext').keyup(function () {
        var messageCount = $(this).val().length;
        //When the count hits

        if (160 - messageCount <= 10) {
            $('span.chars-left').addClass("limit-warning");
        } else {
            $('span.chars-left').removeClass("limit-warning");
        }

        $('span.chars-left').html("Characters left: " + (160 - messageCount));

    });

});

