var Client = require('ssh2').Client;

function newtouch_exec(mode) {
    var conn = new Client();
    conn.on('ready',function () {
        conn.exec(mode,function (err, stream) {
            if(err)
                throw err;
            stream.on('close',function (code, signal) {
                conn.end();
            }).on('data',function (data) {
                console.log('STDOUT: '+data);
            }).stderr.on('data',function (data) {
                console.log('STDERR: '+data);
            });
        });
    }).connect({
        host: '183.66.65.212',
        port: 51022,
        username: 'root',
        password: 'Zsefvgy321'
    });
}

module.exports = newtouch_exec;