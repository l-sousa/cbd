countPrefixes = function () {
    return db.phones.aggregate([ {$group : {_id : "$components.prefix", prefix_counter: {$sum: 1}}}]);
}