const Eos = require("eosjs");
import ScatterJS from "scatterjs-core";
import ScatterEOS from "scatterjs-plugin-eosjs";
import Axios from "axios";
ScatterJS.plugins(new ScatterEOS());

var name,network;
var authority;
var hash1;
var temp;
var timescore;
var token;
var login=false;
network={
  port: 80,
  protocol: "http",
  blockchain: "eos",
  host: "silian.191381.com",
  chainId:
    "cf057bbfb72640471fd910bcb67639c22df9f92470936cddc1ade0e2f2e7dc4f",
  httpEndpoint: "silian.191381.com:80"
}


function Scatterr(productname, httpps) {
  this.eosInstance = Eos({
    chainId: network.chainId,
    httpEndpoint: network.protocol + "://" + network.httpEndpoint,
    broadcast: true,
    verbose: false,
    sign: true
  });
  ScatterJS.scatter.connect(productname).then(connected => {
    this.connected = connected;
    if (this.connected) {
      const requirements = {
        accounts: [network]
      };
      ScatterJS.scatter.getIdentity(requirements)
        .then(identity => {
          if (!identity) {
            console.log(
              `登陆出错：${JSON.stringify(error)}，getIdentity 为空 `
            );
            return;
          }
          console.log(`登陆成功：${JSON.stringify(identity)}`);
          name = identity.accounts[0].name
          authority = identity.accounts[0].authority
          temp = Math.random() * 1000000;
          timescore = parseInt(new Date().getTime() / 1000);
          let memo = [
            name,
            ",",
            temp,
            ",",
            timescore //时间戳
          ];
          var hash = memo.join("");
          hash1 = require("js-sha256").sha256(hash);
          let eos = ScatterJS.scatter.eos(network, Eos);
          eos
            .transaction({
              actions: [{
                account: "magiesupport",
                name: "mark",
                authorization: [{
                  actor: name,
                  permission: authority
                }],
                data: {
                  player: name,
                  message: "login," + hash1,
                  gameowner: "magieboxdemo"
                }
              }]
            }).then(result => {
              Axios({
                method: "post",
                url: httpps +
                  "/login?owner=" +
                  name +
                  "&random=" +
                  temp +
                  "&time=" +
                  timescore +
                  "&transactionId=" +
                  result.transaction_id
              }).then(res => {
                if (res.data.code == 200) {
                  token = res.data.data
                  login=true;
                  layer.open({
                    content: "登陆成功",
                    skin: "msg",
                    time: 2
                  });
                } else {
                  layer.open({
                    content: "请重新登陆",
                    skin: "msg",
                    time: 2
                  });
                }
              });
            })
        })
        .catch(error => {
          console.log(`登陆出错：${JSON.stringify(error)}，未知错误。 `);
        });
    }
  });
}
function ScatterName() {
  return {
    vurrentname: name
  }
}
function Login() {
  return {
    vurrentlogin: login
  }
}
//充值
function Transfer(quantity) {
  let eos = ScatterJS.scatter.eos(network, Eos);
  eos
    .transaction({
      actions: [{
        account: 'eosio.token',
        name: "transfer",
        authorization: [{
          actor: name,
          permission: authority
        }],
        data: {
          from: name,
          to: "magiesupport",
          quantity: quantity,
          memo: 'magieboxdemo,Recharge'
        }
      }]
    })
    .then(result => {
      alert('充值成功')

    })
    .catch(error => {
      alert('充值失败')
    });
}
//提现/转账
function Withdraww(num, message, transferAccount, httpps) {
  var messagee;
  if (num == 1) {
    messagee =
      "withdraw," +
      new Number(message).toFixed(4) +
      " " +
      "EOS,提币测试";
  }
  if (num == 2) {
    messagee =
      "transfer," +
      transferAccount +
      "," +
      new Number(message).toFixed(4) +
      " " +
      "EOS,转账测试";
  }
  let eos = ScatterJS.scatter.eos(network, Eos);
  eos
    .transaction({
      actions: [{
        account: "magiesupport",
        name: "mark",
        authorization: [{
          actor: name,
          permission: authority
        }],
        data: {
          player: name,
          message: messagee,
          gameowner: "magieboxdemo"
        }
      }]
    })
    .then(result => {
      if (num == 1) {
        Axios({
          method: "post",
          headers: {
            token: token
          },
          url: httpps +
            "/account/withdraw?amount=" +
            message +
            "&transactionId=" +
            result.transaction_id
        }).then(res => {
          if (res.data.code == "200" && res.data.status == "success") {
            layer.open({
              content: "提取成功",
              skin: "msg",
              time: 2
            });

          } else if (res.data.code == "406") {
            layer.open({
              content: res.data.status,
              skin: "msg",
              time: 2
            });
          } else {
            layer.open({
              content: "提取失败",
              skin: "msg",
              time: 2
            });
          }
        });
      }
      //转账
      if (num == 2) {
        Axios({
          method: "post",
          headers: {
            token: token
          },
          url: httpps +
            "/account/forward?to=" +
            transferAccount +
            "&transactionId=" +
            result.transaction_id +
            "&amount=" +
            message
        }).then(res => {
          if (res.data.code == "200" && res.data.status == "success") {
            layer.open({
              content: "转账成功",
              skin: "msg",
              time: 2
            });
          } else if (res.data.code == "406") {
            layer.open({
              content: res.data.status,
              skin: "msg",
              time: 2
            });
          } else {
            layer.open({
              content: "转账失败",
              skin: "msg",
              time: 2
            });
          }
        });
      }
    })
    .catch(error => {
      var objs = JSON.parse(error);
      if (objs.code == 500) {
        layer.closeAll();
        layer.open({
          content: "签名失败",
          skin: "msg",
          time: 2
        });
      }
    });
}
window.Transfer = Transfer;
window.Withdraww = Withdraww;
window.Scatterr = Scatterr;
window.ScatterName = ScatterName;
window.Login = Login;