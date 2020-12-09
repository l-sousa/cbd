// Procura por capícuas nos números (sem a parte do prefixo)
patternFinder = function () {
    
    var all_nums = db.phones.find({},{"display": 1, "_id": 0}).toArray(); // array de numeros
    var reg = /[\W_]/g; 
    var result = [];

    for (var i = 0 ; i<all_nums.length ; i++){
        var num = all_nums[i].display.split('-'); //num[1] -> num sem prefixo
        var lowRegStr = num[1].replace(reg, '');
        var reverseStr = lowRegStr.split('').reverse().join(''); 
        if(reverseStr === lowRegStr) { // verifica se é capicua
            result.push(num);
        }
    }
    return result; 
}