module.exports = {
    commonError:{
        error: true,
        msg:'terjadi kesalahan pada server'
    },
    commonErrorMsg: (msg) =>{
        return{
            error: true,
            msg: msg
        }
    },
    commonSucsess: {
        error: false,
        msg: 'Berhasil Memuat Permintaan'
    },
    commonSucsessMsg: (msg) => {
        return{
            error: false,
            msg: msg
        }
    },
    commonResult: (data) => {
        return{
            error: false,
            msg: 'Berhasil Memuat Data',
            data : data

        }
    }
};