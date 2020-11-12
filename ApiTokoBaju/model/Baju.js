const mongoose = require('mongoose')

const userSchema = mongoose.Schema({

    kodeBaju: {
        type: String
    },
    stok: {
        type: String
    },
    harga: {
        type: String
    },
    jenisBaju: {
        type: String
    },
    gambar: {
        type: String
    }

})

module.exports = mongoose.model('baju', userSchema)