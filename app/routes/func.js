// var newtouch_exec = require('./51022_ssh');
// newtouch_uname = new newtouch_exec('uname -a');

var Web3 = require("web3");
var web3 = new Web3();

if (web3.currentProvider) {
    web3 = new Web3(web3.currentProvider);
} else {
    // set the provider you want from Web3.providers
    web3 = new Web3(new Web3.providers.HttpProvider("http://183.66.65.212:51045"));
}


function unlockAccount(address, password) {
    return web3.personal.unlockAccount(address, password);
}

function startMiner() {
    return web3.miner.start(1);
}

function stopMiner() {
    return web3.miner.stop();
}

function minerTimes(times) {
    var start = startMiner();
    web3.admin.sleepBlocks(times);
    var stop = stopMiner();
}

function transfer(from, to, value, password) {
    // web3.eth.
}