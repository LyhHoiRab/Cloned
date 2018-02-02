var utils = {
	//复制非空属性
	copyOf: function(source, target){
		if(source !== undefined && target !== undefined){
			$.each(target, function(key){
				if(source[key] !== undefined){
					target[key] = source[key]
				}
			});
		}
	}
};