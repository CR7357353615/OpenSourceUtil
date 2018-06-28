# 使用expect ssh登录
---
## 传输文件
```
#!/usr/bin/expect
set timeout -1
set src_file [lindex $argv 0]
set username [lindex $argv 1]
set dest_host [lindex $argv 2]
set dest_path [lindex $argv 3]
set sudo_password [lindex $argv 4]
set dest_password [lindex $argv 5]

spawn sudo scp $src_file $username@$dest_host:$dest_path
expect {
    "Password:" {send "$sudo_password\r\n"}
    "*@*password:" { send "$dest_password\r\n" }
    "yes/no" { send "yes\r";exp_continue }
}
```

## 登录后远程进行操作
```
#!/usr/bin/expect
set timeout 10
set username1 [lindex $argv 0]
set dest_host1 [lindex $argv 1]
set sudo_password1 [lindex $argv 2]
set dest_password1 [lindex $argv 3]
set src_file [lindex $argv 4]
set dest_path [lindex $argv 5]
set username2 [lindex $argv 6]
set dest_host2 [lindex $argv 7]
set sudo_password2 [lindex $argv 8]
set dest_password2 [lindex $argv 9]

spawn sudo ssh $username1@$dest_host1
expect {
    "Password:" {send "$sudo_password1\r\n"}
    "*@*assword:" { send "$dest_password1\r\n" }
    "yes/no" { send "yes\r";exp_continue }
}
expect {
    "Password:" {send "$sudo_password1\r\n"}
    "*@*password:" { send "$dest_password1\r\n" }
    "yes/no" { send "yes\r";exp_continue }
}
expect {
    "*" {send "pwd\r\n"}
}
expect {
    "*" {send "cd /xxx\r\n"}
}
expect {
    "*" {send "./scp_expect.exp $src_file $username2 $dest_host2 $dest_path $sudo_password2 $dest_password2\r\n"}
}
expect {
    "*" {send "exit\r\n"}
}
expect eof
```

expect安装参考[https://www.cnblogs.com/zhzhang/p/5979944.html](https://www.cnblogs.com/zhzhang/p/5979944.html)
