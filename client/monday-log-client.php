<?php

abstract class LogType
{
    const Error = 0;
    const Warn = 1;
    const Info = 2;
    const Debug = 3;
    const Trace = 4;
    const BeginCheckpoint = 5;
    const EndCheckpoint = 6;
    const Variable = 7;
    const Input = 8;
    const Output = 9;
}

class MondayLog
{
    private $enableLog = true;
    private $url = 'http://localhost:8080/add_log';
    private $location = 'mondaylog.php.client';

    public function __construct($url, $location = null)
    {
        if (!is_null($url)) {
            $this->url = $url;
        }

        if (!is_null($location)) {
            $this->location = $location;
        }
    }

    private function httpResponse($url, $postData)
    {
        if (!$this->enableLog) {
            return;
        }

        $ch = curl_init();
        curl_setopt($ch, CURLOPT_URL, $url);
        curl_setopt($ch, CURLOPT_USERAGENT, 'MondayLogClient/1.0 (+https://github.com/fifilyu/monday-log-server)');
        curl_setopt($ch, CURLOPT_POST, true);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);

        // 如果 libcurl 编译时使用系统标准的名称解析器（ standard system name resolver），那部分的连接仍旧使用以秒计的超时解决方案，最小超时时间还是一秒钟。
        // 此时，设置为999ms会出现错误"Timeout was reached"。设置为1000ms后，错误消失
        curl_setopt($ch, CURLOPT_TIMEOUT_MS, 1000);
        curl_setopt($ch, CURLOPT_CONNECTTIMEOUT_MS, 1000);

        curl_setopt($ch, CURLOPT_POSTFIELDS, $postData);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array(
            "Content-Type: application/json; charset=utf-8",
        ));
        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        $errno = curl_errno($ch);
        $error = curl_error($ch);
        curl_close($ch);

        if ($errno > 0) {
            throw new Exception("[ERROR] cURL Error ($errno): $error");
        } else {
            if ($httpCode != 200) {
                throw new Exception('[ERROR] Send message to Monday Log Server...failed');
            }
        }
    }

    public function beginCheckpoint($checkpoint, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => null,
            "LogType" => LogType::BeginCheckpoint,
            "Checkpoint" => $checkpoint,
            "VarName" => null,
            "VarValue" => null,
        );

        $this->httpResponse($this->url, json_encode($data));
    }

    public function endCheckpoint($checkpoint, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => null,
            "LogType" => LogType::EndCheckpoint,
            "Checkpoint" => $checkpoint,
            "VarName" => null,
            "VarValue" => null,
        );

        $this->httpResponse($this->url, json_encode($data));
    }

    public function variable($name, $value, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => null,
            "LogType" => LogType::Variable,
            "Checkpoint" => null,
            "VarName" => $name,
            "VarValue" => $value,
        );

        $this->httpResponse($this->url, json_encode($data));
    }

    public function input($name, $value, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => null,
            "LogType" => LogType::Input,
            "Checkpoint" => null,
            "VarName" => $name,
            "VarValue" => $value,
        );

        $this->httpResponse($this->url, json_encode($data));
    }

    public function output($name, $value, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => null,
            "LogType" => LogType::Output,
            "Checkpoint" => null,
            "VarName" => $name,
            "VarValue" => $value,
        );

        $this->httpResponse($this->url, json_encode($data));
    }

    public function error($message, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => $message,
            "LogType" => LogType::Error,
            "Checkpoint" => null,
            "VarName" => null,
            "VarValue" => null,
        );

        $this->httpResponse($this->url, json_encode($data));
    }

    public function warn($message, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => $message,
            "LogType" => LogType::Warn,
            "Checkpoint" => null,
            "VarName" => null,
            "VarValue" => null,
        );

        $this->httpResponse($this->url, json_encode($data));
    }

    public function info($message, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => $message,
            "LogType" => LogType::Info,
            "Checkpoint" => null,
            "VarName" => null,
            "VarValue" => null,
        );

        $this->httpResponse($this->url, json_encode($data));
    }

    public function debug($message, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => $message,
            "LogType" => LogType::Debug,
            "Checkpoint" => null,
            "VarName" => null,
            "VarValue" => null,
        );

        $this->httpResponse($this->url, json_encode($data));
    }

    public function trace($message, $location = null)
    {
        $data = array(
            "Location" => is_null($location) ? $this->location : $location,
            "Message" => $message,
            "LogType" => LogType::Trace,
            "Checkpoint" => null,
            "VarName" => null,
            "VarValue" => null,
        );

        $this->httpResponse($this->url, json_encode($data));
    }
}