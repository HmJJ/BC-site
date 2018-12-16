var Migrations = artifacts.require("./Migrations.sol");
var ERC223Token = artifacts.require("./ERC223Token.sol");

module.exports = function(deployer) {
  deployer.deploy(Migrations);
  deployer.deploy(ERC223Token);
};
