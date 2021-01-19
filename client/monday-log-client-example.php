<?php

include_once 'monday-log-client.php';

$log = new MondayLog('http://localhost:8080/add_log', 'foo.com/info');
$log->beginCheckpoint('log test case');
$log->variable('var1', 'value1');
$log->input('var2', 'value2');
$log->output('var3', 'value3');
$log->error('error message');
$log->warn('warn message');
$log->info('info message');
$log->debug('debug message');
$log->trace('trace message');
$log->endCheckpoint('log test case');