<?php
$target_path = '/home/lukom/Sandbox/test/genotyp';
$dane_json = array();
if (isset($_FILES['file'])) {
    if(move_uploaded_file($_FILES['file']['tmp_name'], $target_path)) {
        $dane_json['data'] = trim(shell_exec('/opt/java/bin/java -jar "/home/lukom/Sandbox/java/Elementy-Bioinformatyki/bj/biojava_test/dist/biojava_test.jar"'));
        $dane_json['type'] = $_POST['type'];
        $dane_json['width'] = $_POST['width'];
        $dane_json['height'] = $_POST['height'];
        $uchwyt = fopen('../json/dane.json', 'w');
        fwrite($uchwyt, json_encode($dane_json));
        fclose($uchwyt);
    }
}
header('Location: /');
