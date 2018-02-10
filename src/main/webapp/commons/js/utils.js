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
	},

	//根据ID查询列表对象
	getById: function(property, name, list){
		if(list !== undefined
			&& list !== null
			&& list.length > 0
			&& property !== undefined
			&& property !== null
			&& property !== ''
			&& name !== undefined
			&& name !== null
			&& name !== ''){

			var result = null;

			$.each(list, function(index, obj){
				if(obj[name] === property){
					result = obj;
				}
			});

			return result;
		}
	}
};