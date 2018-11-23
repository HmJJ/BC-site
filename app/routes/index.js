var express = require('express');
var router = express.Router();

var Web3 = require("web3");
var web3 = new Web3();

if (web3.currentProvider) {
    web3 = new Web3(web3.currentProvider);
} else {
    // set the provider you want from Web3.providers
    web3 = new Web3(new Web3.providers.HttpProvider("http://183.66.65.212:51045"));
}

var isConnected  = web3.isConnected();
if(isConnected){
    console.log('node connected');
} else {
    console.log('node not connected');
}

/* GET login page. */
router.get('/', function(req, res, next) {
    res.render('login');
});

/* check login. */
router.get('/login', function(req, res, next) {
    res.render('main');
});

/* GET home page. */
var state;
router.get('/main', function(req, res, next) {

    var accounts = web3.eth.accounts;

    var miners = getBalances(accounts);
    if(web3.eth.mining) {
        state = '正在挖矿';
    }else {
        state = '点击挖矿';
    }

    res.render('index', { miners: miners, state: state});
});

//获取账号余额
var getBalances = function (accounts) {
    var miners = [];
    for(var i in accounts){
        var miner = {
            "address": Object,
            "balance": Object
        };
        var balance = findBalance(accounts[i]);
        miner.address = accounts[i];
        miner.balance = balance;
        miners.push(miner);
    }
    return miners;
}

//挖矿
router.post('/startMiner', function(req, res, next) {

    state = req.body.state;
    if(state === '点击挖矿') {
        state = startMiner();
    }else {
        state = stopMiner();
    }
    res.json({state: state});
});

router.post('/sendTransaction', function (req, res, next) {
    var account1 = req.body.account1,
        account2 = req.body.account2,
        password = req.body.password,
        ether = req.body.ether;
    var isSuccess =  sendTransaction(account1,account2,password,ether);
    res.json({ isSuccess: isSuccess});
})

//查询余额
var findBalance = function (address) {
    return web3.fromWei(web3.eth.getBalance(address),'ether');
}

router.post('/search', function (req, res, next) {
    var balance =  findBalance(req.body.address);
    res.json({ balance: balance });
});

//开始挖矿
var startMiner = function () {
    web3.miner.start(1);
    if(web3.eth.mining) {
        state = "正在挖矿";
    }else {
        state = "点击挖矿";
    }
    return state;
}

//暂停挖矿
var stopMiner = function () {
    web3.miner.stop();
    if(web3.eth.mining) {
        state = "正在挖矿";
    }else {
        state = "点击挖矿";
    }
    return state;
}

//转账
var sendTransaction = function (account1, account2, password, ether) {
    var isSuccess;
    var flag = web3.personal.unlockAccount(account1,password);
    if(flag) {
        web3.eth.sendTransaction({from:account1,to:account2,value:web3.toWei(ether,"ether")});
        isSuccess = '转账成功！';
    }else {
        isSuccess = '转账失败，账户被锁！';
    }
    return isSuccess;
}
module.exports = router;
