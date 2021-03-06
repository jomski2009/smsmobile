/**
 * Created by jome on 2014/02/28.
 */

$(document).ready(function () {
    var baseUrl = "http://localhost:8080/api/v1/";

    //Custom select picker
    $('.selectpicker').selectpicker();

    //Functionality to clear bootstrap modals

    //Functionality to pop the modal window for contacts import.
    $('a.contacts-import').click(function () {
        var groupName = $(this).data('groupname');
        var groupId = $(this).data('groupid');
        $('.modal-header h4').html("Import contacts into " + groupName);
        //$('#addcontacts-form').attr("action", "/contacts/upload");

        $('#addcontacts-form').attr("action", "/contacts/groups/" + groupId + "/addcontacts");
        //$('.modal-body #addcontacts-form').attr("enctype", "multipart/form-data")
    });

    //Functionality to load the list of contacts on the contactslist modal window.
    $('a.contacts-list').click(function (e) {
        var groupId = $(this).data('groupid');
        var requestUrl = baseUrl + "contacts/groups/" + groupId + "/members";

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

            tabledata += '</tbody></table>';
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


    //Functionality to handle the processing of group creation
    $('#addgroups').submit(function (evt) {
        evt.preventDefault();

        var groupAddUrl = baseUrl + "contacts/groups/add";
        var formData = $(this).serializeObject();
        var fd = JSON.stringify(formData);

        console.log(formData);
        console.log(fd);

        $.ajax({type: 'POST', url: groupAddUrl, contentType: 'application/json', data: fd, dataType: 'json'}).done(function (data, status) {
            console.log(data);
            var groupRow = '<tr><td>' + data.name + '</td><td>' + data.recipients.length + '</td><td><div class="btn-group"><button type="button" class="btn btn-primary btn-sm dropdown-toggle" data-toggle="dropdown">Operations <span class="caret"></span></button><ul class="dropdown-menu" role="menu"><li><a class="contacts-import" data:groupid="' + data.groupidString + '" data:groupname="' + data.name + '" href="#import-data" data-toggle="modal" data-target="#import-data">Import data</a></li><li><a class="contacts-list" data:groupid="' + data.groupidString + '" href="#contactlist" data-toggle="modal" data-target="#contactlist">Members</a></li><li class="divider"></li><li><a href="#">Export Data</a></li></ul></div></td></tr>';
            $('#group-listing-table tbody').append(groupRow);
            $('#addgroupform').modal('toggle');
        }).fail(function (data, status, errorThrown) {
            console.log(data);
            alert("Status - " + data.status + ". " + status + ": " + errorThrown);
        });

    });


    //Functionality to process and send bulk sms
    $('#bulkmessagingform').submit(function (event) {
        event.preventDefault();
        //Let the games begin... :)
        var bulksmsUrl = baseUrl + "sms/bulksms";
        var formData = $(this).serializeObject();
        var fd = JSON.stringify(formData);
        console.log(fd);
        $.ajax({type: 'POST', url: bulksmsUrl, contentType: 'application/json', data: fd, dataType: 'json'}).done(function (data, status) {
            console.log(data);
        }).fail(function (data, status, errorThrown) {
            console.log(data);
        });

    });

    //Functionality to add a selected placeholder to the bulk sms message box.
    $('#field-placeholder').change(function () {
        var placeholder = $('#field-placeholder option:selected').val();
        $('#bulkmessagetext').textrange('insert', "[" + placeholder + "]");
        $('#field-placeholder').prop('selectedIndex', 0);
        $('#field-placeholder').selectpicker('refresh');
    });

    //Functionality to display name of chosen Bulk sms group
    $('#grouppicker.selectpicker').change(function () {
        var groupName = $('#grouppicker option:selected').text();
        var desc = $('#grouppicker option:selected').data('groupdescription');
        $('#chosengroup').html("<p>Recipient Group: " + groupName + " ( " + desc + " )</p>");
        var date = new Date();
        $('#messagedescription').val(groupName + "-" + date.toTimeString());
    });

    $('#bulkmessagetext').keyup(function () {
        var content = $(this).val();
        $('#messagepreviewtext').val(content);
    });

    //Clearing inout values when modal form is hidden
    $('#addgroupform').on('hidden.bs.modal', function (e) {
        $('input').val('');
    });


});

