port=8081
pid=$(netstat -nlp | grep :$port | awk '{print $7}' | awk -F"/" '{ print $1 }');
if [  -n  "$pid"  ];  then
    kill $pid;
fi
cd ~/fe-financial
rm -rf fe-financial-system
git clone https://github.com/clflovepanda/fe-financial-system.git
cd ~/fe-financial/fe-financial-system
npm install
npm run build
nohup npm run start &